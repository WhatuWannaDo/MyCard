package com.example.mycard.Project.data.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DictApiObject {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
            .build()
    }
    val api : DictAPI by lazy {
        retrofit.create(DictAPI::class.java)
    }

}