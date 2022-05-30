package com.example.mycard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mycard.Project.MVVM.View.NavGraph.SetupNavGraph
import com.example.mycard.Project.MVVM.ViewModels.CardViewModel
import com.example.mycard.Project.MVVM.ViewModels.FavoriteRecipesViewModel
import com.example.mycard.ui.theme.MyCardTheme
import kotlinx.coroutines.DelicateCoroutinesApi


class MainActivity : ComponentActivity() {
    lateinit var navController : NavHostController
    @DelicateCoroutinesApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this).get(CardViewModel::class.java)
        val favoriteViewModel = ViewModelProvider(this).get(FavoriteRecipesViewModel::class.java)
        setContent {
            MyCardTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    navController = rememberNavController()
                    SetupNavGraph(navHostController = navController, viewModel, this, favoriteViewModel)
                }
            }
        }
    }
}

