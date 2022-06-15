package com.example.mycard.Project.data.di

import com.example.mycard.Project.data.database.CardDAO
import com.example.mycard.Project.data.network.DictAPI
import com.example.mycard.Project.domain.repository.CardRepository
import com.example.mycard.Project.data.repository.CardRepositoryInt
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CardRepositoryModule {

    @Provides
    @Singleton
    fun provideCardRepository(cardDAO: CardDAO, dictAPI: DictAPI) : CardRepositoryInt = CardRepository(cardDAO, dictAPI)
}