package com.example.nightveil.data

object OrderRepository {

    private val orders = mutableListOf<Order>()

    fun addOrder(order: Order) {
        orders.add(0, order)
    }

    fun getOrders(): List<Order> {
        return orders
    }
}