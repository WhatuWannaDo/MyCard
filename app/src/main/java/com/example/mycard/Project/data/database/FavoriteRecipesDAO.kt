package com.example.mycard.Project.data.database

import androidx.room.*
import com.example.mycard.Project.MVVM.Models.FavoriteRecipesModel
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteRecipesDAO {

    @Transaction
    @Query("SELECT * FROM favorite_recipes")
    fun getAllFromFavoriteRecipes() : Flow<List<FavoriteRecipesModel>>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIntoFavoriteRecipes(recipe : FavoriteRecipesModel)

    @Transaction
    @Delete
    fun deleteFromFavoriteRecipes(recipe: FavoriteRecipesModel)

}