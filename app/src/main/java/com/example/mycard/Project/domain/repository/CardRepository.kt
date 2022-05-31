package com.example.mycard.Project.domain.repository

import com.example.mycard.Project.MVVM.Models.*
import com.example.mycard.Project.data.network.DictApiObject
import com.example.mycard.Project.data.database.CardDAO
import com.example.mycard.Project.data.models.databaseModels.CardModel
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
    suspend fun getTextFromApi(apiKey : String, text : String, value:String) : Response<List<HeadModel>> {
        return DictApiObject.api.getTextFromDictApi(apiKey, text, value)
    }

    suspend fun getGroceryFromApi(apiKey : String, text : String, value:String) : Response<GroceryModel>{
        return DictApiObject.api.getGroceryFromApi(apiKey, text, value)
    }

    suspend fun getMenuItemsFromApi(apiKey : String, text : String, value:String) : Response<MenuItemsModel>{
        return DictApiObject.api.getMenuItemsApi(apiKey, text, value)
    }

    suspend fun getRecipesFromApi(
        apiKey : String,
        text : String,
        cuisine : String?,
        diet : String?,
        intolerances : String?,
        equipment : String?,
        includeIngredients : String?,
        excludeIngredients : String?,
        type : String?,
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
        value : String
        ) : Response<RecipesModel>{
        return DictApiObject.api.getRecipesApi(
            apiKey,
            text,
            cuisine,
            diet,
            intolerances,
            equipment,
            includeIngredients,
            excludeIngredients,
            type,
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
            value
        )
    }

    suspend fun getByURL(url: String) : Response<RecipesModel>{
        return DictApiObject.api.getByURL(url)
    }
}