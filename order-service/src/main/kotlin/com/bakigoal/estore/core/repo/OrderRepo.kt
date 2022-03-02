package com.bakigoal.estore.core.repo

import com.bakigoal.estore.core.entity.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepo: JpaRepository<OrderEntity, String> {
}