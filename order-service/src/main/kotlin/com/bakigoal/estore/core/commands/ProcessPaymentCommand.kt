package com.bakigoal.estore.core.commands

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class ProcessPaymentCommand(

    @TargetAggregateIdentifier
    val orderId: String,
    var userId: String = "",
    val paymentId: String
)