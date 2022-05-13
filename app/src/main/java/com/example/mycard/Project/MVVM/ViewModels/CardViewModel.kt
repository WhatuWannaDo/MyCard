package com.example.mycard.Project.MVVM.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycard.Project.MVVM.Models.CardModel
import com.example.mycard.Project.Room.Data.DataBase.DataBase
import com.example.mycard.Project.Room.Repository.CardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CardViewModel(application: Application) : AndroidViewModel(application) {

    val getAllProducts : Flow<List<CardModel>>
    private val cardRepository : CardRepository

    init {
        val cardDAO = DataBase.getDatabase(application).cardDao()
        cardRepository = CardRepository(cardDAO = cardDAO)
        getAllProducts = cardRepository.getAllProducts
    }

    fun addProduct(cardModel: CardModel){
        viewModelScope.launch(Dispatchers.IO) {
            cardRepository.addProduct(cardModel = cardModel)
        }
    }

    fun deleteProduct(cardModel: CardModel){
        viewModelScope.launch(Dispatchers.IO) {
            cardRepository.deleteProduct(cardModel = cardModel)
        }
    }

    fun deleteAllProducts(){
        cardRepository.deleteAllProducts()
    }
}