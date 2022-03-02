package com.bakigoal.estore.command.rest

data class OrderRequestDto(
    var productId: String = "",
    var quantity: Int = 0,
    var addressId: String = ""
)