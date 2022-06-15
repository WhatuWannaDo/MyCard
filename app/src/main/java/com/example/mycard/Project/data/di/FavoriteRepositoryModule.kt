package com.example.mycard.Project.data.di

import com.example.mycard.Project.data.database.FavoriteRecipesDAO
import com.example.mycard.Project.data.repository.FavoriteRepositoryInt
import com.example.mycard.Project.domain.repository.FavoriteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FavoriteRepositoryModule {

    @Provides
    @Singleton
    fun providesFavoriteRepository(dao: FavoriteRecipesDAO) : FavoriteRepositoryInt = FavoriteRepository(dao)

}