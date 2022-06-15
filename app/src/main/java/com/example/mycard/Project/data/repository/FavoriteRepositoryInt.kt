package com.example.mycard.Project.data.repository

import com.example.mycard.Project.MVVM.Models.FavoriteRecipesModel
import kotlinx.coroutines.flow.Flow

interface FavoriteRepositoryInt {

    val getAllRecipes : Flow<List<FavoriteRecipesModel>>

    suspend fun insertIntoRecipes(recipesModel: FavoriteRecipesModel) : Unit

    suspend fun deleteFromRecipes(recipesModel: FavoriteRecipesModel) : Unit
}