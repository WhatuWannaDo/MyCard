package com.example.mycard.Project.MVVM.View

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.mycard.Project.MVVM.View.Screens.Screens
import com.example.mycard.Project.MVVM.ViewModels.CardViewModel

@Composable
fun RecipesSearchScreen(navController: NavController, cardViewModel: CardViewModel){

    Scaffold(topBar = { TopAppBarRecipes(navController = navController) }) {
    }
}

@Composable
fun TopAppBarRecipes(navController: NavController){
    val buttonMode : Color
    val iconMode = Color.White
    if (isSystemInDarkTheme()){
        buttonMode = Color(39,39,39)
    }else{
        buttonMode = Color(98,0,238)
    }
    TopAppBar(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Recipes", fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.h5.fontSize)
        Spacer(Modifier.weight(1f, true))
        Icon(imageVector = Icons.Default.Search, contentDescription = "Search Recipes", tint = iconMode, modifier = Modifier.clickable {
            navController.navigate(Screens.RecipesSearchValues.route)
        })
        Button(onClick = { navController.navigate(route = Screens.Settings.route) }, colors = ButtonDefaults.buttonColors(backgroundColor = buttonMode)) {
            Icon(imageVector = Icons.Default.Settings, contentDescription = "SettingsRecipes", tint = iconMode)
        }
    }

}
