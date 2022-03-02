package com.bakigoal.estore.order.core.events

data class PaymentProcessedEvent(
    val orderId: String,
    var productId: String = ""
)