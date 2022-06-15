package com.example.mycard.Project.data.repository

import com.example.mycard.Project.MVVM.Models.GroceryModel
import com.example.mycard.Project.MVVM.Models.HeadModel
import com.example.mycard.Project.MVVM.Models.MenuItemsModel
import com.example.mycard.Project.MVVM.Models.RecipesModel
import com.example.mycard.Project.data.database.CardDAO
import com.example.mycard.Project.data.models.databaseModels.CardModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CardRepositoryInt {

    val getAllProducts : Flow<List<CardModel>>

    suspend fun addProduct(cardModel: CardModel) : Unit

    suspend fun deleteProduct(cardModel: CardModel) : Unit

    suspend fun deleteAllProducts() : Unit

    suspend fun getTextFromApi(apiKey : String, text : String, value:String) : Response<List<HeadModel>>

    suspend fun getGroceryFromApi(apiKey : String, text : String, value:String) : Response<GroceryModel>

    suspend fun getMenuItemsFromApi(apiKey : String, text : String, value:String) : Response<MenuItemsModel>

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
        value : String) : Response<RecipesModel>

    suspend fun getByURL(url: String) : Response<RecipesModel>
}