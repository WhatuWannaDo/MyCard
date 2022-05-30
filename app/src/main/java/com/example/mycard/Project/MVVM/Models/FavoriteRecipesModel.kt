package com.example.mycard.Project.MVVM.Models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_recipes")
data class FavoriteRecipesModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val nutrients: List<NutrientsFavoriteModel>
)

data class NutrientsFavoriteModel(
    @PrimaryKey(autoGenerate = true)
    val nutrientsId : Int,
    val name : String,
    val amount : Double,
    val unit : String
)
