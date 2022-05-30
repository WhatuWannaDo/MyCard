package com.example.mycard.Project.MVVM.Models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_recipes")
data class FavoriteRecipesModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val image : String,
    val nutrients: List<NutrientsFavoriteModel>?
)

data class NutrientsFavoriteModel(
    val name : String,
    val amount : Double,
    val unit : String
)
