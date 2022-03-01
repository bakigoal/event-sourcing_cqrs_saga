package com.bakigoal.estore.product.rest

import com.bakigoal.estore.product.command.CreateProductCommand
import com.bakigoal.estore.product.dto.Product
import com.bakigoal.estore.product.repo.ProductRepo
import org.axonframework.commandhandling.gateway.CommandGateway
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/product")
class ProductController(
    val env: Environment,
    val commandGateway: CommandGateway,
    val productRepo: ProductRepo
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ProductController::class.java)
    }

    @GetMapping
    fun get(): List<Product> {
        logger.info("get product")
        return productRepo.findAll().map { Product(title = it.title, price = it.price, quantity = it.quantity) }
    }

    @PostMapping
    fun createProduct(@RequestBody product: Product): String {
        val createProductCommand = CreateProductCommand(
            productId = UUID.randomUUID().toString(),
            title = product.title,
            price = product.price,
            quantity = product.quantity
        )
        logger.info("sending $createProductCommand")
        return commandGateway.sendAndWait(createProductCommand)
    }

    @PutMapping("/{id}")
    fun updateProduct(@RequestBody body: String, @PathVariable id: String): String {
        return "updated product with id=$id => $body"
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: String): String {
        return "deleted product with id=$id"
    }
}