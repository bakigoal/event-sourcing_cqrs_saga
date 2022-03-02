package com.bakigoal.estore.command

import com.bakigoal.estore.core.events.OrderApprovedEvent
import com.bakigoal.estore.core.events.OrderCreatedEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Aggregate
class OrderAggregate() {

    @AggregateIdentifier
    private var orderId: String? = null
    private var productId: String? = null
    private var userId: String? = null
    private var quantity: Int? = null
    private var addressId: String? = null
    private var orderStatus: OrderStatus? = null

    companion object {
        val logger: Logger = LoggerFactory.getLogger(OrderAggregate::class.java)
    }

    @CommandHandler
    constructor(command: CreateOrderCommand) : this() {
        val orderCreatedEvent = command.toOrderCreatedEvent()
        logger.info("producing event $orderCreatedEvent")
        AggregateLifecycle.apply(orderCreatedEvent)
    }

    @CommandHandler
    fun handle(orderApprovedEvent: OrderApprovedEvent){
        AggregateLifecycle.apply(OrderApprovedEvent(orderApprovedEvent.orderId, OrderStatus.APPROVED))
    }

    @EventSourcingHandler
    fun on(event: OrderCreatedEvent) {
        orderId = event.orderId
        productId = event.productId
        userId = event.userId
        quantity = event.quantity
        addressId = event.addressId
        orderStatus = event.orderStatus
    }

    @EventSourcingHandler
    fun on(event: OrderApprovedEvent) {
        orderStatus = event.orderStatus
    }

}

private fun CreateOrderCommand.toOrderCreatedEvent() =
    OrderCreatedEvent(orderId, userId, productId, quantity, addressId, orderStatus)
