package com.example.nightveil.data

object WishlistRepository {

    private val wishlist =
        mutableListOf<Product>()

    fun addProduct(product: Product) {

        if (!wishlist.any { it.id == product.id }) {
            wishlist.add(product)
        }
    }

    fun removeProduct(productId: Int) {

        wishlist.removeAll {
            it.id == productId
        }
    }

    fun isFavorite(productId: Int): Boolean {

        return wishlist.any {
            it.id == productId
        }
    }

    fun getWishlist(): List<Product> {

        return wishlist
    }
}