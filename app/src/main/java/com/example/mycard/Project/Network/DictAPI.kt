package com.example.mycard.Project.Network

import com.example.mycard.Project.MVVM.Models.GroceryModel
import com.example.mycard.Project.MVVM.Models.HeadModel
import com.example.mycard.Project.MVVM.Models.MenuItemsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "7e843a8220f14d5ba2891e686e661e9a"

interface DictAPI {

    @GET("food/ingredients/autocomplete")
    suspend fun getTextFromDictApi(
        @Query("apiKey") apiKey : String,
        @Query("query") text : String,
        @Query("number") value : String
        ): Response<List<HeadModel>>

    @GET("food/products/search")
    suspend fun getGroceryFromApi(
        @Query("apiKey") apiKey: String,
        @Query("query") query : String,
        @Query("number") value: String
    ) : Response<GroceryModel>

    @GET("food/menuItems/search")
    suspend fun getMenuItemsApi(
        @Query("apiKey") apiKey: String,
        @Query("query") query : String,
        @Query("number") value: String
    ) : Response<MenuItemsModel>
}