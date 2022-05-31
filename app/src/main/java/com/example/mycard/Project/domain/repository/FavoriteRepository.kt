package com.example.mycard.Project.domain.repository

import com.example.mycard.Project.MVVM.Models.FavoriteRecipesModel
import com.example.mycard.Project.data.database.FavoriteRecipesDAO
import kotlinx.coroutines.flow.Flow

class FavoriteRepository(private val dao : FavoriteRecipesDAO) {
    val getAllRecipes : Flow<List<FavoriteRecipesModel>> = dao.getAllFromFavoriteRecipes()

    suspend fun insertIntoRecipes(recipesModel: FavoriteRecipesModel){
        dao.insertIntoFavoriteRecipes(recipesModel)
    }

    suspend fun deleteFromRecipes(recipesModel: FavoriteRecipesModel){
        dao.deleteFromFavoriteRecipes(recipesModel)
    }

}