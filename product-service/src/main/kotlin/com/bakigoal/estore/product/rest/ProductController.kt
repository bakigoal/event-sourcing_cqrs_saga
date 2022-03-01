package com.bakigoal.estore.product.rest

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/product")
class ProductController(@Autowired val env: Environment) {

    companion object{
        val logger: Logger = LoggerFactory.getLogger(ProductController::class.java)
    }

    @GetMapping
    fun get(): String {
        logger.info("get product")
        return "get product from ${env.getProperty("local.server.port")}"
    }

    @PostMapping
    fun createProduct(@RequestBody body: String): String {
        return "created product $body"
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