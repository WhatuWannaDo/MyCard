package com.example.mycard.Project.MVVM.ViewModels

import android.app.Application
import androidx.lifecycle.*
import com.example.mycard.Project.MVVM.Models.*
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
    var myResponse: MutableLiveData<Response<List<HeadModel>>> = MutableLiveData()
    val groceryResponse : MutableLiveData<Response<GroceryModel>> = MutableLiveData()
    val menuItemsResponse : MutableLiveData<Response<MenuItemsModel>> = MutableLiveData()
    val recipesResponse : MutableLiveData<Response<RecipesModel>> = MutableLiveData()


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

    fun getTextApi(apiKey : String, text : String, value:String) {
        viewModelScope.launch{
            val response = cardRepository.getTextFromApi(apiKey, text, value)
            myResponse.value = response
        }
    }

    fun getGroceryApi(apiKey : String, text : String, value:String){
        viewModelScope.launch {
            val response = cardRepository.getGroceryFromApi(apiKey, text, value)
            groceryResponse.value = response
        }
    }

    fun getMenuItemsApi(apiKey : String, text : String, value:String){
        viewModelScope.launch {
            val response = cardRepository.getMenuItemsFromApi(apiKey, text, value)
            menuItemsResponse.value = response
        }
    }
    fun getRecipesApi(
        apiKey : String,
        text : String,
        cuisine : String?,
        diet : String?,
        intolerances : String?,
        equipment : String?,
        includeIngredients : String?,
        excludeIngredients : String?,
        type : String?,
        instructionsRequired : Boolean,
        addRecipeInformation : Boolean,
        titleMatch : String?,
        maxReadyTime : String?,
        minCarbs : String?,
        maxCarbs : String?,
        minProtein : String?,
        maxProtein : String?,
        minCalories : String?,
        maxCalories : String?,
        minFat : String?,
        maxFat : String?,
        minSugar : String?,
        maxSugar : String?,
        value:String
    ){
        viewModelScope.launch {
            val response = cardRepository.getRecipesFromApi(
                apiKey,
                text,
                cuisine,
                diet,
                intolerances,
                equipment,
                includeIngredients,
                excludeIngredients,
                type,
                instructionsRequired,
                addRecipeInformation,
                titleMatch,
                maxReadyTime,
                minCarbs,
                maxCarbs,
                minProtein,
                maxProtein,
                minCalories,
                maxCalories,
                minFat,
                maxFat,
                minSugar,
                maxSugar,
                value)
            recipesResponse.value = response
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

