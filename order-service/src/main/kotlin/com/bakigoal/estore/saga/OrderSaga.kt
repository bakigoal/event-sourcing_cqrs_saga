package com.bakigoal.estore.saga

import com.bakigoal.estore.core.commands.ApproveOrderCommand
import com.bakigoal.estore.core.commands.ProcessPaymentCommand
import com.bakigoal.estore.core.commands.ReserveProductCommand
import com.bakigoal.estore.core.events.OrderApprovedEvent
import com.bakigoal.estore.core.events.OrderCreatedEvent
import com.bakigoal.estore.core.events.PaymentProcessedEvent
import com.bakigoal.estore.core.events.ProductReservedEvent
import org.axonframework.commandhandling.CommandCallback
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.modelling.saga.EndSaga
import org.axonframework.modelling.saga.SagaEventHandler
import org.axonframework.modelling.saga.StartSaga
import org.axonframework.spring.stereotype.Saga
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import java.util.*
import java.util.concurrent.TimeUnit

@Saga
class OrderSaga {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(OrderSaga::class.java)
    }

    @Autowired
    @Transient
    lateinit var commandGateway: CommandGateway

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    fun handle(orderCreatedEvent: OrderCreatedEvent) {
        val reserveProductCommand = ReserveProductCommand(
            productId = orderCreatedEvent.productId,
            quantity = orderCreatedEvent.quantity,
            orderId = orderCreatedEvent.orderId,
            userId = orderCreatedEvent.userId
        )
        commandGateway.send(
            reserveProductCommand,
            CommandCallback<ReserveProductCommand, Any> { _, commandResultMessage ->
                if (commandResultMessage.isExceptional) {
                    // start compensating transaction
                    logger.error("Exception OrderCreatedEvent, starting compensating transaction...$commandResultMessage")
                }
            })
    }

    @SagaEventHandler(associationProperty = "orderId")
    fun handle(productReservedEvent: ProductReservedEvent) {
        logger.info("Event: $productReservedEvent")
        val processPaymentCommand = ProcessPaymentCommand(
            orderId = productReservedEvent.orderId,
            userId = productReservedEvent.userId,
            paymentId = UUID.randomUUID().toString()
        )
        try {
            commandGateway.sendAndWait<Any>(processPaymentCommand, 10, TimeUnit.SECONDS)
        } catch (e: Exception) {
            // start compensating transaction
            logger.error("Exception ProductReservedEvent, starting compensating transaction... $e")
        }
    }

    @SagaEventHandler(associationProperty = "paymentId")
    fun handle(paymentProcessedEvent: PaymentProcessedEvent) {
        logger.info("Event: $paymentProcessedEvent")
        val approveOrderCommand = ApproveOrderCommand(paymentProcessedEvent.orderId)
        commandGateway.send<Any>(approveOrderCommand)
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    fun handle(orderApprovedEvent: OrderApprovedEvent) {
        logger.info("Event: $orderApprovedEvent")
    }
}