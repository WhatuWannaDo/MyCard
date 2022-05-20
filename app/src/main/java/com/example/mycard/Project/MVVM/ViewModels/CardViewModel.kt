package com.example.mycard.Project.MVVM.ViewModels

import android.app.Application
import androidx.lifecycle.*
import com.example.mycard.Project.MVVM.Models.CardModel
import com.example.mycard.Project.MVVM.Models.DictModel
import com.example.mycard.Project.MVVM.Models.HeadModel
import com.example.mycard.Project.Room.Data.DataBase.DataBase
import com.example.mycard.Project.Room.Repository.CardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class CardViewModel(application: Application) : AndroidViewModel(application) {

    val getAllProducts : Flow<List<CardModel>>
    private val cardRepository : CardRepository
    var myResponse: MutableLiveData<Response<HeadModel>> = MutableLiveData()

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

    fun getTextApi(apiKey : String, lang : String, text:String) {
        viewModelScope.launch{
            val response = cardRepository.getTextFromApi(apiKey, lang, text)
            myResponse.value = response
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

