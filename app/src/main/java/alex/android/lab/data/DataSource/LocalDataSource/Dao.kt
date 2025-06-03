package alex.android.lab.data.DataSource.LocalDataSource


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Dao {
    @Insert
    suspend fun insertProduct(product: ProductInListEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProducts(products: List<ProductInListEntity>)

    @Query("SELECT * FROM products")
    suspend  fun getProducts(): List<ProductInListEntity>

    @Query("SELECT * FROM products WHERE guid = :guid")
    suspend fun getProductByGuid(guid: String): ProductInListEntity

    @Query("UPDATE products SET isFavorite = :isFavorite WHERE guid = :guid")
    suspend fun updateFavoriteStatus(guid: String, isFavorite: Boolean)

    @Query("UPDATE products SET viewCount = viewCount + 1 WHERE guid = :guid")
    suspend fun incrementViewCount(guid: String)

    @Query("UPDATE products SET inCartCount = :inCartCount WHERE guid = :guid")
    suspend fun updateInCartCount(guid: String, inCartCount: Int)

    @Query("SELECT inCartCount FROM products WHERE guid = :guid")
    suspend fun getInCartCount(guid: String): Int

    @Query("SELECT COUNT(*) FROM products WHERE inCartCount > 0")
    suspend fun getInCartProductsCount(): Int
}