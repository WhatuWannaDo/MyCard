package com.example.mycard

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mycard.Project.MVVM.View.MainWindow
import com.example.mycard.Project.MVVM.View.NavGraph.SetupNavGraph
import com.example.mycard.Project.MVVM.ViewModels.CardViewModel
import com.example.mycard.ui.theme.MyCardTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    lateinit var navController : NavHostController
    @DelicateCoroutinesApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this).get(CardViewModel::class.java)
        setContent {
            MyCardTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    navController = rememberNavController()
                    SetupNavGraph(navHostController = navController, viewModel, this)
                }
            }
        }
    }
}

