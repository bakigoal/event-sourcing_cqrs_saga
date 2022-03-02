package com.bakigoal.estore.product.command

import com.bakigoal.estore.product.core.entity.ProductLookupEntity
import com.bakigoal.estore.product.core.events.ProductCreatedEvent
import com.bakigoal.estore.product.core.repo.ProductLookupRepo
import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@ProcessingGroup("product-group")
class ProductLookupEventsHandler(
    @Autowired val productLookupRepo: ProductLookupRepo
) {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(ProductLookupEventsHandler::class.java)
    }

    @EventHandler
    fun on(event: ProductCreatedEvent) {
        val lookupEntity = ProductLookupEntity(productId = event.productId, title = event.title)
        logger.info("saving $lookupEntity")
        productLookupRepo.save(lookupEntity)
    }
}