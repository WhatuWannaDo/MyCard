package com.example.mycard.Project.MVVM.Models

data class MenuItemsModel(
    val menuItems : List<MenuItemsOptions>
)

data class MenuItemsOptions(
    val id : Int,
    val title : String,
    val restaurantChain : String,
    val image : String,
    val imageType : String,
    val servings : ServingsOptions
)
data class ServingsOptions(
    val number : Int,
    val size : Double,
    val unit : String
)
