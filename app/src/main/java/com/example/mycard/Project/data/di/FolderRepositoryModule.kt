package com.example.mycard.Project.data.di

import com.example.mycard.Project.data.database.FolderDAO
import com.example.mycard.Project.data.repository.FolderRepositoryInt
import com.example.mycard.Project.domain.repository.FolderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FolderRepositoryModule {

    @Provides
    @Singleton
    fun providesFolderRepository(dao: FolderDAO) : FolderRepositoryInt = FolderRepository(dao)

}