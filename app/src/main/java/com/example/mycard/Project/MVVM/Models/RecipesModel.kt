package com.example.mycard.Project.MVVM.Models

data class RecipesModel(
    val results : List<ResultInfo>
)
data class ResultInfo(
    val id : Int,
    val title : String,
    val image : String,
    val imageType : String,
    val nutrition : List<NutritionInfo>,
    val offset : Int,
    val number : Int,
    val totalResults : Int
)
data class NutritionInfo(
    val name : String,
    val amount : Int,
    val unit : String
)
