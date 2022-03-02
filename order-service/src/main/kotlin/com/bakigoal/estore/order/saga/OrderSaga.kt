package com.bakigoal.estore.order.saga

import com.bakigoal.estore.order.core.events.OrderApprovedEvent
import com.bakigoal.estore.order.core.events.OrderCreatedEvent
import com.bakigoal.estore.order.core.events.PaymentProcessedEvent
import com.bakigoal.estore.order.core.events.ProductReservedEvent
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.modelling.saga.EndSaga
import org.axonframework.modelling.saga.SagaEventHandler
import org.axonframework.modelling.saga.StartSaga
import org.axonframework.spring.stereotype.Saga
import org.springframework.beans.factory.annotation.Autowired

@Saga
class OrderSaga {

    @Autowired
    @Transient
    lateinit var commandGateway: CommandGateway

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    fun handle(orderCreatedEvent: OrderCreatedEvent) {

    }

    @SagaEventHandler(associationProperty = "productId")
    fun handle(productReservedEvent: ProductReservedEvent) {

    }
    @SagaEventHandler(associationProperty = "paymentId")
    fun handle(paymentProcessedEvent: PaymentProcessedEvent) {

    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    fun handle(orderApprovedEvent: OrderApprovedEvent) {

    }
}