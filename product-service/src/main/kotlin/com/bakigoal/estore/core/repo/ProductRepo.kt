package com.bakigoal.estore.core.repo

import com.bakigoal.estore.core.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ProductRepo: JpaRepository<ProductEntity, String> {

    fun findFirstByTitle(title: String): Optional<ProductEntity>
}