package com.example.database_impl

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.database_api.models.ProductInListEntity

@Database(
    entities = [ProductInListEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MainRoomDB : RoomDatabase() {
    abstract fun getDao(): Dao

    companion object {
        @Volatile
        private var INSTANCE: MainRoomDB? = null

        fun getDB(context: Context): MainRoomDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainRoomDB::class.java,
                    "main_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}