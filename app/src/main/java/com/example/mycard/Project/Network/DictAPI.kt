package com.example.mycard.Project.Network

import com.example.mycard.Project.MVVM.Models.GroceryModel
import com.example.mycard.Project.MVVM.Models.HeadModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

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
}