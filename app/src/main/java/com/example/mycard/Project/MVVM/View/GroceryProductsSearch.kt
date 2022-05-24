package com.example.mycard.Project.MVVM.View

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.mycard.Project.MVVM.View.Screens.Screens

@Composable
fun GroceryProductsSearchScreen(navController: NavController){
    Column() {
        Text(text = "Grocery Products Search")
        Button(onClick = { navController.navigate(route = Screens.Settings.route) }) {
            Text(text = "Settings")
        }
    }
}