package com.example.mycard.Project.MVVM.Models

data class GroceryModel(
    val products : List<NameGroceryModel>
)
data class NameGroceryModel(
    val id : Int,
    val title : String
)
