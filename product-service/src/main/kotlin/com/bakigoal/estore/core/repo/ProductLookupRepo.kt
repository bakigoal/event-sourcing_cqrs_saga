package com.bakigoal.estore.core.repo

import com.bakigoal.estore.core.entity.ProductLookupEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ProductLookupRepo : JpaRepository<ProductLookupEntity, String> {

    fun findFirstByProductIdOrTitle(id: String, title: String): Optional<ProductLookupEntity>
}