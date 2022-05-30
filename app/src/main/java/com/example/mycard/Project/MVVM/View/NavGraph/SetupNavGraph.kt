package com.example.mycard.Project.MVVM.View.NavGraph

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mycard.MainActivity
import com.example.mycard.Project.MVVM.View.*
import com.example.mycard.Project.MVVM.View.Screens.DETAIL_OBJECT_VAlUE
import com.example.mycard.Project.MVVM.View.Screens.FavoriteScreen
import com.example.mycard.Project.MVVM.View.Screens.Screens
import com.example.mycard.Project.MVVM.ViewModels.CardViewModel
import com.example.mycard.Project.MVVM.ViewModels.FavoriteRecipesViewModel
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun SetupNavGraph(navHostController: NavHostController, cardViewModel: CardViewModel, obj : MainActivity, favoriteRecipesViewModel: FavoriteRecipesViewModel) {
    NavHost(navController = navHostController, startDestination = getDest(obj = obj)
    ){
        composable(route = Screens.Home.route, content = {
            MainWindow(cardViewModel = cardViewModel, obj = obj, navHostController)
        })
        composable(route = Screens.Settings.route, content = {
            SettingsTab(navHostController, obj = obj)
        })
        composable(route = Screens.MenuItemsSearch.route, content = {
            MenuItemSearchScreen(navHostController, cardViewModel, obj)
        })
        composable(route = Screens.GroceryProductsSearch.route, content = {
            GroceryProductsSearchScreen(navHostController, cardViewModel, obj)
        })
        composable(route = Screens.RecipesSearch.route, arguments = listOf(navArgument(DETAIL_OBJECT_VAlUE){
            type = NavType.StringType
        }), content = {
            val argument = it.arguments?.getString(DETAIL_OBJECT_VAlUE).toString()
            RecipesSearchScreen(navHostController, argument, cardViewModel, obj, favoriteRecipesViewModel)
        })
        composable(route = Screens.RecipesSearchValues.route, content = {
            RecipesValuesScreen(navHostController, cardViewModel, obj)
        })
        composable(route = Screens.FavoriteRecipes.route, content = {
            FavoriteScreen(navHostController, favoriteRecipesViewModel)
        })
    }
}

//getting destinations from shared prefs
fun getDest(obj: MainActivity) : String {
    val sharedPrefsSettings : SharedPreferences = obj.getSharedPreferences("Category", Context.MODE_PRIVATE)
    when(sharedPrefsSettings.getString("SelectedCategory", "Ingredient Search").toString()){
        "Recipes Search" -> return Screens.RecipesSearch.route
        "Ingredient Search" -> return Screens.Home.route
        "Grocery Products Search" -> return Screens.GroceryProductsSearch.route
        "Menu Items Search" -> return Screens.MenuItemsSearch.route
    }
    return "No data"
}

