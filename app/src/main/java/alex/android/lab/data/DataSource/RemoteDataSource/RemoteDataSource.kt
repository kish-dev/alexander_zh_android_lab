package alex.android.lab.data.DataSource.RemoteDataSource

import alex.android.lab.data.DataSource.RemoteDataSource.RetrofitClient.ProductsApiService
import alex.android.lab.data.dto.ProductInListDTO
import alex.android.lab.domain.ApiResult.ApiResult
import java.io.IOException
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
   private val api: ProductsApiService
) {
    suspend fun getProducts(): ApiResult<List<ProductInListDTO>> {
        return try {
            val response = api.getProducts()
            if (response.isSuccessful) {
                val products = response.body()
                if (products != null) {
                    ApiResult.Success(products)
                } else {
                    ApiResult.Error("No products")
                }
            } else {
                val errorMsg = response.errorBody()?.string() ?: "No error message"
                ApiResult.Error(errorMsg)
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message.toString())
        } catch (e: IOException) {
            ApiResult.Error(e.message.toString())
        }
    }
}
