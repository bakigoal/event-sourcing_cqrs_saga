package com.bakigoal.estore.order.core.repo

import com.bakigoal.estore.order.core.entity.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepo: JpaRepository<OrderEntity, String> {
}