package com.bakigoal.estore.core.commands

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class ApproveOrderCommand(

    @TargetAggregateIdentifier
    val orderId: String
)