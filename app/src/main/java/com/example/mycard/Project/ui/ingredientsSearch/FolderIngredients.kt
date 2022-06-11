package com.example.mycard.Project.ui.ingredientsSearch

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mycard.Project.data.models.databaseModels.CardModel
import com.example.mycard.Project.ui.screens.Screens
import com.google.gson.Gson

@ExperimentalMaterialApi
@Composable
fun FolderIngredients(navController: NavController, argument : String){
    Scaffold(topBar = {
        TopAppBarFolderIngredients(navController = navController)
    }) {
        Ingredients(argument)
    }
}

@Composable
fun TopAppBarFolderIngredients(navController : NavController){
    val buttonMode : Color
    val iconMode = Color.White
    if (isSystemInDarkTheme()){
        buttonMode = Color(39,39,39)
    }else{
        buttonMode = Color(98,0,238)
    }
    TopAppBar(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = { navController.navigate(route = Screens.Home.route) }, colors = ButtonDefaults.buttonColors(backgroundColor = buttonMode)) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "BackToHome", tint = iconMode)
        }
        Text(text = "Ingredients", fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.h5.fontSize)
    }
}

@ExperimentalMaterialApi
@Composable
fun Ingredients(argument: String){
    val backgroundMode : Color
    if (isSystemInDarkTheme()){
        backgroundMode = Color(20,20,20)
    }else{
        backgroundMode = Color.White
    }
    val parsedValue = Gson().fromJson(argument, Array<CardModel>::class.java)?.toList()

    LazyColumn(contentPadding = PaddingValues(vertical = 20.dp, horizontal = 5.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(parsedValue!!){
            ListItem(
                modifier = Modifier
                    .border(2.dp, color = Color.Green, shape = RoundedCornerShape(15.dp))
                    .background(backgroundMode),
                text = { Text(text = it.productName, fontSize = MaterialTheme.typography.h6.fontSize) },
                trailing = { Text(text = "Amount: " + it.productAmount, fontSize = MaterialTheme.typography.h6.fontSize) }
            )
        }
    }
}