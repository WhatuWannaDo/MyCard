package com.example.mycard.Project.ui.screens


const val DETAIL_OBJECT_VAlUE = "responseObject"
const val FOLDER_INGREDIENTS_VALUE = "folderValue"

sealed class Screens(val route : String){
    object Home : Screens(route = "home_route")
    object Settings : Screens(route ="settings_route")
    object MenuItemsSearch : Screens(route = "menu_items_route")
    object GroceryProductsSearch : Screens(route = "grocery_products_route")
    object RecipesSearch : Screens(route = "recipes_search/{$DETAIL_OBJECT_VAlUE}"){
        fun passObject(responseObject : String) : String {
            return this.route.replace(oldValue = "{$DETAIL_OBJECT_VAlUE}", newValue = responseObject)
        }
    }
    object RecipesSearchValues : Screens(route = "recipes_search_values")
    object FavoriteRecipes : Screens(route = "favorite_recipes_route")
    object FolderIngredients : Screens(route = "folder_recipes/{$FOLDER_INGREDIENTS_VALUE}"){
        fun passIngredients(folderValue : String) : String{
            return this.route.replace(oldValue = "{$FOLDER_INGREDIENTS_VALUE}", newValue = folderValue)
        }
    }
}
