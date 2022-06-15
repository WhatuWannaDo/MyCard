package com.example.mycard.Project.domain.repository

import com.example.mycard.Project.MVVM.Models.*
import com.example.mycard.Project.data.database.CardDAO
import com.example.mycard.Project.data.models.databaseModels.CardModel
import com.example.mycard.Project.data.network.DictAPI
import com.example.mycard.Project.data.repository.CardRepositoryInt
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardRepository @Inject constructor(private val cardDAO: CardDAO, private val dictAPI: DictAPI) :
    CardRepositoryInt {

    override val getAllProducts : Flow<List<CardModel>> = cardDAO.getAllProducts()

    override suspend fun addProduct(cardModel: CardModel){
        return cardDAO.addProduct(cardModel = cardModel)
    }

    override suspend fun deleteProduct(cardModel: CardModel){
        return cardDAO.deleteProduct(cardModel = cardModel)
    }

    override suspend fun deleteAllProducts(){
        return cardDAO.deleteAllProducts()
    }
    override suspend fun getTextFromApi(apiKey : String, text : String, value:String) : Response<List<HeadModel>> {
        return dictAPI.getTextFromDictApi(apiKey, text, value)
    }

    override suspend fun getGroceryFromApi(apiKey : String, text : String, value:String) : Response<GroceryModel>{
        return dictAPI.getGroceryFromApi(apiKey, text, value)
    }

    override suspend fun getMenuItemsFromApi(apiKey : String, text : String, value:String) : Response<MenuItemsModel>{
        return dictAPI.getMenuItemsApi(apiKey, text, value)
    }

    override suspend fun getRecipesFromApi(
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
        return dictAPI.getRecipesApi(
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

    override suspend fun getByURL(url: String) : Response<RecipesModel>{
        return dictAPI.getByURL(url)
    }
}