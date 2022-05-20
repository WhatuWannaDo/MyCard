package com.example.mycard.Project.Room.Repository

import com.example.mycard.Project.MVVM.Models.CardModel
import com.example.mycard.Project.MVVM.Models.DictModel
import com.example.mycard.Project.MVVM.Models.HeadModel
import com.example.mycard.Project.Network.DictApiObject
import com.example.mycard.Project.Room.Data.DAO.CardDAO
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class CardRepository(private val cardDAO: CardDAO) {
    val getAllProducts : Flow<List<CardModel>> = cardDAO.getAllProducts()

    suspend fun addProduct(cardModel: CardModel){
        cardDAO.addProduct(cardModel = cardModel)
    }

    suspend fun deleteProduct(cardModel: CardModel){
        cardDAO.deleteProduct(cardModel = cardModel)
    }

    suspend fun deleteAllProducts(){
        cardDAO.deleteAllProducts()
    }
    suspend fun getTextFromApi(apiKey : String, lang : String, text:String) : Response<HeadModel> {
        return DictApiObject.api.getTextFromDictApi(apiKey, lang, text)
    }

}