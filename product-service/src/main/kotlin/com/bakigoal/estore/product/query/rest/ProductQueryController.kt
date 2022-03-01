package com.bakigoal.estore.product.query.rest

import com.bakigoal.estore.product.core.dto.Product
import com.bakigoal.estore.product.core.repo.ProductRepo
import org.axonframework.commandhandling.gateway.CommandGateway
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/product")
class ProductQueryController(
    val commandGateway: CommandGateway,
    val productRepo: ProductRepo
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ProductQueryController::class.java)
    }

    @GetMapping
    fun get(): List<Product> {
        logger.info("get product")
        return productRepo.findAll().map { Product(title = it.title, price = it.price, quantity = it.quantity) }
    }
}