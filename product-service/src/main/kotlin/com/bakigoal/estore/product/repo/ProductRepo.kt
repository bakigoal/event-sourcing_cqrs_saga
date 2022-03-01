package com.bakigoal.estore.product.repo

import com.bakigoal.estore.product.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ProductRepo: JpaRepository<ProductEntity, String> {

    fun findFirstByTitle(title: String): Optional<ProductEntity>
}