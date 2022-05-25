package com.example.mycard.Project.MVVM.View.NavGraph

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mycard.MainActivity
import com.example.mycard.Project.MVVM.Models.GroceryModel
import com.example.mycard.Project.MVVM.View.*
import com.example.mycard.Project.MVVM.View.Screens.Screens
import com.example.mycard.Project.MVVM.ViewModels.CardViewModel
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun SetupNavGraph(navHostController: NavHostController, cardViewModel: CardViewModel, obj : MainActivity) {
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
        composable(route = Screens.RecipesSearch.route, content = {
            RecipesSearchScreen(navHostController)
        })
        composable(route = Screens.RecipesSearchValues.route, content = {
            RecipesValuesScreen(navHostController)
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

