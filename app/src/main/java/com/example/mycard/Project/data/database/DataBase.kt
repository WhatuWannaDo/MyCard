package com.example.mycard.Project.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mycard.Project.data.models.databaseModels.CardModel
import com.example.mycard.Project.MVVM.Models.FavoriteRecipesModel
import com.example.mycard.Project.MVVM.util.TypeConverterFolder
import com.example.mycard.Project.MVVM.util.TypeConverterRecipes
import com.example.mycard.Project.data.models.databaseModels.FolderModel

@Database(entities = [CardModel::class, FavoriteRecipesModel::class, FolderModel::class], exportSchema = false, version = 12)
@TypeConverters(TypeConverterRecipes::class, TypeConverterFolder::class)
abstract class DataBase : RoomDatabase() {
    abstract fun cardDao() : CardDAO
    abstract fun FavoriteRecipesDAO() : FavoriteRecipesDAO
    abstract fun folderDAO() : FolderDAO

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