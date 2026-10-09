package com.example.nightveil.data

import com.example.nightveil.api.RetrofitClient

class ProductApiRepository {

    suspend fun getProducts(): Result<List<Product>> {

        return try {

            val products =
                RetrofitClient.apiService.getProducts()

            Result.success(products)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }
}