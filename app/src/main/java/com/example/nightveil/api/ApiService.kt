package com.example.nightveil.api

import com.example.nightveil.data.Product
import retrofit2.http.GET

interface ApiService {

    @GET("rest/v1/products?select=*")
    suspend fun getProducts(): List<Product>
}