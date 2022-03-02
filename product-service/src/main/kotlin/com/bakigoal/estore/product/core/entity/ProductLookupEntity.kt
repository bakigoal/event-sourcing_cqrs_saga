package com.bakigoal.estore.product.core.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "productLookup")
data class ProductLookupEntity(
    @Id
    var productId: String? = null,

    @Column(unique = true)
    var title: String = ""
)