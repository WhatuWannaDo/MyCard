package com.example.mycard.Project.domain.repository

import com.example.mycard.Project.MVVM.Models.FavoriteRecipesModel
import com.example.mycard.Project.data.database.FavoriteRecipesDAO
import com.example.mycard.Project.data.repository.FavoriteRepositoryInt
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepository @Inject constructor(private val dao : FavoriteRecipesDAO) : FavoriteRepositoryInt{
    override val getAllRecipes : Flow<List<FavoriteRecipesModel>> = dao.getAllFromFavoriteRecipes()

    override suspend fun insertIntoRecipes(recipesModel: FavoriteRecipesModel){
        dao.insertIntoFavoriteRecipes(recipesModel)
    }

    override suspend fun deleteFromRecipes(recipesModel: FavoriteRecipesModel){
        dao.deleteFromFavoriteRecipes(recipesModel)
    }

}