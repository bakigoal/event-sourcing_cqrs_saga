package com.bakigoal.estore.query

import com.bakigoal.estore.core.repo.ProductRepo
import com.bakigoal.estore.query.rest.ProductResponseDto
import org.axonframework.queryhandling.QueryHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ProductsQueryHandler(
    @Autowired val productRepo: ProductRepo
) {

    @QueryHandler
    fun findProducts(findProductsQuery: FindProductsQuery): List<ProductResponseDto> {
        return productRepo.findAll()
            .map { ProductResponseDto(title = it.title, price = it.price, quantity = it.quantity) }
    }
}