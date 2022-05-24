package com.example.mycard.Project.MVVM.View.Screens

sealed class Screens(val route : String){
    object Home : Screens(route = "home_route")
    object Settings : Screens(route ="settings_route")
    object MenuItemsSearch : Screens(route = "menu_items_route")
    object GroceryProductsSearch : Screens(route = "grocery_products_route")
    object RecipesSearch : Screens(route = "recipes_search")
}
