package com.bakigoal.estore.command

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class CreateProductCommand(

    @TargetAggregateIdentifier
    val productId: String,
    var title: String = "",
    var price: Double = 0.0,
    var quantity: Int = 0
)