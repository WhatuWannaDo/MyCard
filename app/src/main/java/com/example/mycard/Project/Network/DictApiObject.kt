package com.example.mycard.Project.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DictApiObject {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://dictionary.yandex.net/api/v1/dicservice.json/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api : DictAPI by lazy {
        retrofit.create(DictAPI::class.java)
    }

}