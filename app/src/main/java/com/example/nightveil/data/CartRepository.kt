package com.example.nightveil.data

object CartRepository {

    private val cartItems = mutableListOf<CartItem>()

    fun addProduct(product: Product) {

        val existingItem = cartItems.find {
            it.product.id == product.id
        }

        if (existingItem != null) {
            existingItem.quantity++
        } else {
            cartItems.add(
                CartItem(
                    product = product,
                    quantity = 1
                )
            )
        }
    }

    fun getItems(): MutableList<CartItem> {
        return cartItems
    }

    fun increaseQuantity(productId: Int) {

        val item = cartItems.find {
            it.product.id == productId
        }

        if (item != null) {
            item.quantity++
        }
    }

    fun decreaseQuantity(productId: Int) {

        val item = cartItems.find {
            it.product.id == productId
        }

        if (item != null) {

            if (item.quantity > 1) {
                item.quantity--
            } else {
                cartItems.remove(item)
            }
        }
    }

    fun getTotalPrice(): Int {

        return cartItems.sumOf {
            it.product.price * it.quantity
        }
    }

    fun clearCart() {
        cartItems.clear()
    }
}