package com.bakigoal.estore.product.command

import com.bakigoal.estore.order.core.commands.ReserveProductCommand
import com.bakigoal.estore.product.core.events.ProductCreatedEvent
import com.bakigoal.estore.product.core.events.ProductReservedEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils

@Aggregate
class ProductAggregate() {

    @AggregateIdentifier
    private var productId: String = ""
    private var title: String = ""
    private var price: Double = 0.0
    private var quantity: Int = 0

    companion object{
        val logger: Logger = LoggerFactory.getLogger(ProductAggregate::class.java)
    }

    @CommandHandler
    constructor(createProductCommand: CreateProductCommand) : this() {
        logger.info("handling $createProductCommand")
        // validate create Product Command
        if (createProductCommand.price<=0) {
            throw IllegalArgumentException("Price cannot be less than or equal to 0")
        }
        if (createProductCommand.title.isBlank()) {
            throw IllegalArgumentException("Title cannot be blank")
        }

        val productCreatedEvent = ProductCreatedEvent()
        BeanUtils.copyProperties(createProductCommand, productCreatedEvent)

        logger.info("producing event $productCreatedEvent")
        AggregateLifecycle.apply(productCreatedEvent)
    }

    @CommandHandler
    fun handle(reserveProductCommand: ReserveProductCommand) {
        if(quantity < reserveProductCommand.quantity){
            throw IllegalArgumentException("Not enough quantity")
        }

        val productReservedEvent = ProductReservedEvent()
        BeanUtils.copyProperties(productReservedEvent, reserveProductCommand)

        logger.info("producing event $productReservedEvent")
        AggregateLifecycle.apply(productReservedEvent)
    }

    @EventSourcingHandler
    fun on(productCreatedEvent: ProductCreatedEvent) {
        productId = productCreatedEvent.productId
        title = productCreatedEvent.title
        price = productCreatedEvent.price
        quantity = productCreatedEvent.quantity
    }

    @EventSourcingHandler
    fun on(productReservedEvent: ProductReservedEvent) {
        quantity = quantity.minus(productReservedEvent.quantity)
    }

}