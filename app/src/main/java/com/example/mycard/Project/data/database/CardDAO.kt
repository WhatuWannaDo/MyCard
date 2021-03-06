package com.example.mycard.Project.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mycard.Project.data.models.databaseModels.CardModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDAO {

    @Query("SELECT * FROM card_products")
    fun getAllProducts() : Flow<List<CardModel>>

    @Insert
    suspend fun addProduct(cardModel: CardModel)

    @Delete
    suspend fun deleteProduct(cardModel: CardModel)

    @Query("DELETE FROM card_products")
    suspend fun deleteAllProducts()

}