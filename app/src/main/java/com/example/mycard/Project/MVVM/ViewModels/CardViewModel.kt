package com.example.mycard.Project.MVVM.ViewModels

import android.app.Application
import androidx.cardview.widget.CardView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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

    suspend fun deleteAllProducts(){
        viewModelScope.launch(Dispatchers.IO) {
            cardRepository.deleteAllProducts()
        }
    }

    class CardViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            if (modelClass.isAssignableFrom(CardViewModel::class.java)) {
                return CardViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

