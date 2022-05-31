package com.example.mycard.Project.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mycard.Project.data.models.databaseModels.CardModel
import com.example.mycard.Project.MVVM.Models.FavoriteRecipesModel
import com.example.mycard.Project.MVVM.util.TypeConverterRecipes

@Database(entities = [CardModel::class, FavoriteRecipesModel::class], exportSchema = false, version = 9)
@TypeConverters(TypeConverterRecipes::class)
abstract class DataBase : RoomDatabase() {
    abstract fun cardDao() : CardDAO
    abstract fun FavoriteRecipesDAO() : FavoriteRecipesDAO

    companion object{
        @Volatile
        private var INSTANCE : DataBase? = null

        fun getDatabase(context: Context): DataBase {
            val tempInstance = INSTANCE

            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context, DataBase::class.java, "MyCardDataBase")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}