package com.bakigoal.estore.product.rest

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/product")
class ProductController {

    @GetMapping
    fun get(): String {
        return "get product"
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