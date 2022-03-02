package com.bakigoal.estore.order.core.commands

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class ReserveProductCommand(

    @TargetAggregateIdentifier
    var productId: String = "",
    var quantity: Int = 0,
    val orderId: String,
    var userId: String = ""
)