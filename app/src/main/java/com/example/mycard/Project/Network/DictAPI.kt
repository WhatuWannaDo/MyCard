package com.example.mycard.Project.Network

import com.example.mycard.Project.MVVM.Models.HeadModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DictAPI {

    @GET("autocomplete")
    suspend fun getTextFromDictApi(
        @Query("apiKey") apiKey : String,
        @Query("query") text : String,
        @Query("number") value : String
        ): Response<List<HeadModel>>

}