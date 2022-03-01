package com.bakigoal.estore.product.core.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "products")
data class ProductEntity(

    @Id
    var productId: String? = null,

    @Column(unique = true)
    var title: String = "",

    var price: Double = 0.0,

    var quantity: Int = 0
)