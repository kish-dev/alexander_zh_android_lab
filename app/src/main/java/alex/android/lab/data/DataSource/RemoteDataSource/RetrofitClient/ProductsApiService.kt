package alex.android.lab.data.DataSource.RemoteDataSource.RetrofitClient

import alex.android.lab.data.dto.ProductInListDTO
import retrofit2.Response
import retrofit2.http.GET

interface ProductsApiService {
    @GET("products1")
    suspend fun getProducts(): Response<List<ProductInListDTO>>
}