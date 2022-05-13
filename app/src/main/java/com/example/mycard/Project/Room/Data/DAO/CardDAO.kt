package com.example.mycard.Project.Room.Data.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mycard.Project.MVVM.Models.CardModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDAO {

    @Query("SELECT * FROM card_products")
    fun getAllProducts() : Flow<List<CardModel>>

    @Insert
    fun addProduct(cardModel: CardModel)

    @Delete
    fun deleteProduct(cardModel: CardModel)

    @Query("DELETE FROM card_products")
    fun deleteAllProducts()

}