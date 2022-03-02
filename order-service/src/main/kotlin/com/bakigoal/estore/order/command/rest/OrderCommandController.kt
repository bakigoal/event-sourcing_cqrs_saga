package com.bakigoal.estore.order.command.rest

import com.bakigoal.estore.order.command.CreateOrderCommand
import com.bakigoal.estore.order.command.OrderStatus
import org.axonframework.commandhandling.gateway.CommandGateway
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

const val USER_ID = "27b95829-4f3f-4ddf-8983-151ba010e35b"

@RestController
@RequestMapping("/api/v1/order")
class OrdersController {

    @Autowired
    lateinit var commandGateway: CommandGateway

    companion object {
        val logger: Logger = LoggerFactory.getLogger(OrdersController::class.java)
    }

    @PostMapping
    fun createOrder(@RequestBody order: OrderRequestDto): String {
        val createOrderCommand = CreateOrderCommand(
            orderId = UUID.randomUUID().toString(),
            userId = USER_ID,
            productId = order.productId,
            quantity = order.quantity,
            addressId = order.addressId,
            orderStatus = OrderStatus.CREATED
        )

        logger.info("sending $createOrderCommand")
        return commandGateway.sendAndWait(createOrderCommand)
    }
}