package com.bakigoal.estore.order.query

import com.bakigoal.estore.order.core.entity.OrderEntity
import com.bakigoal.estore.order.core.events.OrderCreatedEvent
import com.bakigoal.estore.order.core.repo.OrderRepo
import org.axonframework.eventhandling.EventHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class OrderEventsHandler {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(OrderEventsHandler::class.java)
    }

    @Autowired
    lateinit var orderRepo: OrderRepo

    @EventHandler
    fun on(event: OrderCreatedEvent) {
        val entity: OrderEntity = event.toEntity()
        logger.info("saving order $entity")
        orderRepo.save(entity)
    }
}

private fun OrderCreatedEvent.toEntity() =
    OrderEntity(orderId, userId, productId, quantity, addressId, orderStatus)
