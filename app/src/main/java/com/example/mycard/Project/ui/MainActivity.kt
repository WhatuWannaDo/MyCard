package com.example.mycard.Project.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mycard.Project.MVVM.View.NavGraph.SetupNavGraph
import com.example.mycard.Project.ui.viewModels.CardViewModel
import com.example.mycard.Project.ui.viewModels.FavoriteRecipesViewModel
import com.example.mycard.Project.ui.theme.MyCardTheme
import com.example.mycard.Project.ui.viewModels.FolderViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var navController : NavHostController
    @DelicateCoroutinesApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this).get(CardViewModel::class.java)
        val favoriteViewModel = ViewModelProvider(this).get(FavoriteRecipesViewModel::class.java)
        val folderViewModel = ViewModelProvider(this).get(FolderViewModel::class.java)
        setContent {
            MyCardTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    navController = rememberNavController()
                    SetupNavGraph(navHostController = navController, viewModel, this, favoriteViewModel, folderViewModel)
                }
            }
        }
    }
}

