package com.bakigoal.estore.query

import com.bakigoal.estore.core.entity.OrderEntity
import com.bakigoal.estore.core.events.OrderApprovedEvent
import com.bakigoal.estore.core.events.OrderCreatedEvent
import com.bakigoal.estore.core.repo.OrderRepo
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

    @EventHandler
    fun on(event: OrderApprovedEvent) {
        val order = orderRepo.findById(event.orderId).orElseThrow()
        order.orderStatus = event.orderStatus
        orderRepo.save(order)
    }
}

private fun OrderCreatedEvent.toEntity() =
    OrderEntity(orderId, userId, productId, quantity, addressId, orderStatus)
