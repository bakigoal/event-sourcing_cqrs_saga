package com.bakigoal.estore.command

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class CreateOrderCommand(

    @TargetAggregateIdentifier
    val orderId: String,
    var userId: String = "",
    var productId: String = "",
    var quantity: Int = 0,
    var addressId: String = "",
    var orderStatus: OrderStatus
)