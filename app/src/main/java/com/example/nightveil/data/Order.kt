package com.example.nightveil.data

data class Order(
    val orderId: String,
    val date: String,
    val total: Int,
    val paymentMethod: String,
    val address: String
)