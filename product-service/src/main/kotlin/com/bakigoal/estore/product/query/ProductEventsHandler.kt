package com.bakigoal.estore.product.query

import com.bakigoal.estore.product.core.entity.ProductEntity
import com.bakigoal.estore.product.core.events.ProductCreatedEvent
import com.bakigoal.estore.product.core.repo.ProductRepo
import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@ProcessingGroup("product-group")
class ProductEventsHandler(
    @Autowired val productRepo: ProductRepo
) {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(ProductEventsHandler::class.java)
    }

    @EventHandler
    fun on(event: ProductCreatedEvent) {
        val entity = ProductEntity(
            productId = event.productId,
            title = event.title,
            price = event.price,
            quantity = event.quantity
        )
        logger.info("saving product $entity")
        productRepo.save(entity)
    }
}