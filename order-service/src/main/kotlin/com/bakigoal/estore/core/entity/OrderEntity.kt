package com.bakigoal.estore.core.entity

import com.bakigoal.estore.command.OrderStatus
import javax.persistence.*

@Entity
@Table(name= "orders")
data class OrderEntity(
    @Id
    var orderId: String? = null,
    var userId: String = "",
    var productId: String = "",
    var quantity: Int = 0,
    var addressId: String = "",
    @Enumerated(EnumType.STRING)
    var orderStatus: OrderStatus
)