package com.bakigoal.estore.product.command

import com.bakigoal.estore.product.events.ProductCreatedEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
import java.lang.IllegalArgumentException

@Aggregate
class ProductAggregate() {

    @AggregateIdentifier
    private var productId: String? = null
    private var title: String? = null
    private var price: Double? = null
    private var quantity: Int? = null

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

    @EventSourcingHandler
    fun on(productCreatedEvent: ProductCreatedEvent) {
        productId = productCreatedEvent.productId
        title = productCreatedEvent.title
        price = productCreatedEvent.price
        quantity = productCreatedEvent.quantity
    }

}