package com.example.mycard.Project.Room.Repository

import com.example.mycard.Project.MVVM.Models.CardModel
import com.example.mycard.Project.Room.Data.DAO.CardDAO
import kotlinx.coroutines.flow.Flow

class CardRepository(private val cardDAO: CardDAO) {
    val getAllProducts : Flow<List<CardModel>> = cardDAO.getAllProducts()

    suspend fun addProduct(cardModel: CardModel){
        cardDAO.addProduct(cardModel = cardModel)
    }

    suspend fun deleteProduct(cardModel: CardModel){
        cardDAO.deleteProduct(cardModel = cardModel)
    }

    fun deleteAllProducts(){
        cardDAO.deleteAllProducts()
    }
}