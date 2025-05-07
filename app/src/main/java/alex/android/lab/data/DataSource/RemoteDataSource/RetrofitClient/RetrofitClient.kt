package alex.android.lab.data.DataSource.RemoteDataSource.RetrofitClient

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val PRODUCTS_URL = "https://675d888763b05ed079781f3e.mockapi.io/api/"

object RetrofitClient {

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
        .baseUrl(PRODUCTS_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    }

    val productsApiService: ProductsApiService by lazy {
        retrofit.create(ProductsApiService::class.java)
    }
}