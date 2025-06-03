package alex.android.lab.data.DataSource.LocalDataSource

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "products")
data class ProductInListEntity (
    @PrimaryKey(autoGenerate = false) var guid: String,
    @ColumnInfo(name = "image") var image: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "price") var price: String,
    @ColumnInfo(name = "rating") var rating: Double,
    @ColumnInfo(name = "isFavorite") var isFavorite: Boolean,
    @ColumnInfo(name = "isInCart") var isInCart: Boolean,
    @ColumnInfo(name = "viewCount") var viewCount: Int,
    @ColumnInfo(name = "inCartCount") var inCartCount: Int = 0
)


