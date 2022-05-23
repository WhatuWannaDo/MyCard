package com.example.mycard.Project.MVVM.View.NavGraph

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mycard.MainActivity
import com.example.mycard.Project.MVVM.View.MainWindow
import com.example.mycard.Project.MVVM.View.Screens.Screens
import com.example.mycard.Project.MVVM.View.SettingsTab
import com.example.mycard.Project.MVVM.ViewModels.CardViewModel
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun SetupNavGraph(navHostController: NavHostController, cardViewModel: CardViewModel, obj : MainActivity) {
    NavHost(navController = navHostController, startDestination = Screens.Home.route
    ){
        composable(route = Screens.Home.route, content = {
            MainWindow(cardViewModel = cardViewModel, obj = obj, navHostController)
        })
        composable(route = Screens.Settings.route, content = {
            SettingsTab(navHostController)
        })
    }
}