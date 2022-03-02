package com.bakigoal.estore.order.saga

import com.bakigoal.estore.order.core.commands.ReserveProductCommand
import com.bakigoal.estore.order.core.events.OrderApprovedEvent
import com.bakigoal.estore.order.core.events.OrderCreatedEvent
import com.bakigoal.estore.order.core.events.PaymentProcessedEvent
import com.bakigoal.estore.order.core.events.ProductReservedEvent
import org.axonframework.commandhandling.CommandCallback
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.modelling.saga.EndSaga
import org.axonframework.modelling.saga.SagaEventHandler
import org.axonframework.modelling.saga.StartSaga
import org.axonframework.spring.stereotype.Saga
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

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
        commandGateway.send(reserveProductCommand, CommandCallback<ReserveProductCommand, Any> {
                _, commandResultMessage ->
            if(commandResultMessage.isExceptional) {
                // start compensating transaction
                logger.error("Exception occur, starting compensating transaction...")
            }
        })
    }

    @SagaEventHandler(associationProperty = "orderId")
    fun handle(productReservedEvent: ProductReservedEvent) {
        logger.info("Event: $productReservedEvent")
    }

    @SagaEventHandler(associationProperty = "paymentId")
    fun handle(paymentProcessedEvent: PaymentProcessedEvent) {
        logger.info("Event: $paymentProcessedEvent")
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    fun handle(orderApprovedEvent: OrderApprovedEvent) {
        logger.info("Event: $orderApprovedEvent")
    }
}