package com.example.nightveil.data

object ProductRepository {

    var products: List<Product> = emptyList()
        private set

    fun setProducts(
        newProducts: List<Product>
    ) {
        products = newProducts
    }
}