package com.example.nightveil.data

import com.google.gson.annotations.SerializedName

data class Product(
    val id: Int,
    val name: String,
    val category: String,
    val price: Int,
    val rating: Double = 0.0,
    val sold: Int = 0,
    val description: String = "",

    @SerializedName("image_url")
    val imageUrl: String = ""
)