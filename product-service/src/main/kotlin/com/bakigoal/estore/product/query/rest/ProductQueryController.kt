package com.bakigoal.estore.product.query.rest

import com.bakigoal.estore.product.query.FindProductsQuery
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/product")
class ProductQueryController(
    val queryGateway: QueryGateway,
) {

    @GetMapping
    fun getAll(): List<ProductResponseDto> {
        val productsQuery = FindProductsQuery()
        return queryGateway.query(
            productsQuery,
            ResponseTypes.multipleInstancesOf(ProductResponseDto::class.java)
        ).join()
    }
}