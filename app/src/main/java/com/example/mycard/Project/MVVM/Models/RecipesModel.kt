package com.example.mycard.Project.MVVM.Models

import com.google.gson.annotations.SerializedName

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
    val analyzedInstructionsRecipe : AnalyzedInstructionsStep
)
data class AnalyzedInstructionsStep(
    val name : String,
    val steps : List<AnalyzeStepsRecipes>
)

data class AnalyzeStepsRecipes(
    @SerializedName("number")
    val stepNumber : Int,
    @SerializedName("step")
    val stepTitle : String,
    val ingredients : List<IngredientsRecipe>,
    @SerializedName("equipment")
    val equipmentSteps : List<EquipmentRecipe>
)
data class EquipmentRecipe(
    @SerializedName("id")
    val idIngredients : Int,
    @SerializedName("name")
    val nameIngredient : String,
    val localizedName : String,
    @SerializedName("image")
    val imageIngredient : String
)

data class IngredientsRecipe(
    @SerializedName("id")
    val idIngredients : Int,
    @SerializedName("name")
    val nameIngredient : String,
    val localizedName : String,
    @SerializedName("image")
    val imageIngredient : String
)

data class Nutrients(
    val nutrients : List<NutritionInfo>
)
data class NutritionInfo(
    val name : String,
    val amount : Double,
    val unit : String
)
