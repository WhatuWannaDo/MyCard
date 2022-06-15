package com.example.mycard.Project.data.di

import com.example.mycard.Project.data.network.DictAPI
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object DictApiObject {

    @Provides
    @Singleton
    fun provideRetrofit() = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
            .build()

    @Provides
    @Singleton
    fun provideDictApi(retrofit: Retrofit) : DictAPI = retrofit.create()


}