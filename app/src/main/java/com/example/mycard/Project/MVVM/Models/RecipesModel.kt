package com.example.mycard.Project.MVVM.Models

data class RecipesModel(
    val results : List<ResultInfo>
)
data class ResultInfo(
    val id : Int,
    val title : String,
    val image : String,
    val imageType : String,
    val nutrition : Nutrients,
    val offset : Int,
    val number : Int,
    val totalResults : Int
)
data class Nutrients(
    val nutrients : List<NutritionInfo>
)
data class NutritionInfo(
    val name : String,
    val amount : Double,
    val unit : String
)
