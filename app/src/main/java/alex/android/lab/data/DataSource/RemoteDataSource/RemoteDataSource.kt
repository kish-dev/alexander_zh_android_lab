package alex.android.lab.data.DataSource.RemoteDataSource

import alex.android.lab.data.DataSource.RemoteDataSource.RetrofitClient.ProductsApiService
import alex.android.lab.data.dto.ProductInListDTO
import alex.android.lab.domain.UiStates.ApiResult
import java.io.IOException

class RemoteDataSource(
   private val api: ProductsApiService
) {
    suspend fun getProducts(): ApiResult<List<ProductInListDTO>>? {
        return try {
            val response = api.getProducts()
            if (response.isSuccessful) {
                val products = response.body()
                if (products != null) {
                    ApiResult.Success(products)
                } else {
                    ApiResult.Error("Products not found", response.code())
                }
            } else {
                val errorMsg = response.errorBody()?.string() ?: "No error message"
                ApiResult.Error(errorMsg, response.code())
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message.toString())
        } catch (e: IOException) {
            ApiResult.Error(e.message.toString())
        }
    }
}
