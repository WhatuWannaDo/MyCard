package com.example.mycard.Project.Network

import com.example.mycard.Project.MVVM.Models.DictModel
import com.example.mycard.Project.MVVM.Models.HeadModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface DictAPI {

    @GET("lookup")
    suspend fun getTextFromDictApi(
        @Query("key") apiKey : String,
        @Query("lang") lang : String,
        @Query("text") text : String
        ): Response<HeadModel>

}