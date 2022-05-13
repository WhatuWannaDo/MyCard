package com.example.mycard.Project.Room.Data.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mycard.Project.MVVM.Models.CardModel
import com.example.mycard.Project.Room.Data.DAO.CardDAO

@Database(entities = [CardModel::class], exportSchema = false, version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun cardDao() : CardDAO

    companion object{
        @Volatile
        private var INSTANCE : DataBase? = null

        fun getDatabase(context: Context): DataBase{
            val tempInstance = INSTANCE

            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context, DataBase::class.java, "JKXDataBase")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}