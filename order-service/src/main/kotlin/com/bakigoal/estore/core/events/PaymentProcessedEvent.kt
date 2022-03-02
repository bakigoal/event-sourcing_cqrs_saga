package com.bakigoal.estore.core.events

data class PaymentProcessedEvent(
    val orderId: String,
    var userId: String = "",
    val paymentId: String
)