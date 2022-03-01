package com.bakigoal.estore.product.command.rest

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

data class ProductRequestDto(
    @field:NotBlank(message = "Title should not be blank")
    var title: String,

    @field:Min(value = 1, message = "Price cannot be lower than 1")
    var price: Double = 0.0,

    @field:Min(value = 1, message = "Quantity cannot be lower than 1")
    @field:Max(value = 5, message = "Quantity cannot be higher than 5")
    var quantity: Int = 0
)