package com.example.mycard.Project.MVVM.Models


data class RecipesModel(
    val results : List<ResultInfo>
)
data class ResultInfo(
    val id : Int,
    val title : String,
    val image : String,
    val imageType : String,
    val nutrition : Nutrients?,
    val offset : Int,
    val number : Int,
    val totalResults : Int,
    val sourceUrl : String,
    val analyzedInstructions : List<AnalyzedInstructionsIngredients>
)

data class Nutrients(
    val nutrients : List<NutritionInfo>
)
data class NutritionInfo(
    val name : String,
    val amount : Double,
    val unit : String
)
data class AnalyzedInstructionsIngredients(
    val steps : List<AnalyzeSteps>
)
data class AnalyzeSteps(
    val ingredients : List<AnalyzedIngredients>
)
data class AnalyzedIngredients(
    val id : Int,
    val name : String,
    val localizedName : String,
    val image : String
)