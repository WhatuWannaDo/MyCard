package com.example.mycard.Project.Room.Repository

import com.example.mycard.Project.MVVM.Models.FavoriteRecipesModel
import com.example.mycard.Project.Room.Data.DAO.FavoriteRecipesDAO
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