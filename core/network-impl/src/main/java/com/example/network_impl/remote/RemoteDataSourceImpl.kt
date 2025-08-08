package com.example.network_impl.remote

import com.example.network_api.models.NetworkApiResult
import com.example.network_api.models.ProductInListNetworkDto
import com.example.network_api.remote.ApiService
import com.example.network_api.remote.RemoteDataSourceApi
import java.io.IOException
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val api: ApiService
): RemoteDataSourceApi {
    override suspend fun getProducts(): NetworkApiResult<List<ProductInListNetworkDto>> {
        return try {
            val response = api.getProducts()
            if (response.isSuccessful) {
                val products = response.body()
                if (products != null) {
                    NetworkApiResult.Success(products)
                } else {
                    NetworkApiResult.Error("No products")
                }
            } else {
                val errorMsg = response.errorBody()?.string() ?: "No error message"
                NetworkApiResult.Error(errorMsg)
            }
        } catch (e: Exception) {
            NetworkApiResult.Error(e.message.toString())
        } catch (e: IOException) {
            NetworkApiResult.Error(e.message.toString())
        }
    }
}