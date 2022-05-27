package com.example.mycard.Project.MVVM.View

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.example.mycard.MainActivity
import com.example.mycard.Project.MVVM.View.Screens.Screens
import com.example.mycard.Project.MVVM.ViewModels.CardViewModel
import com.example.mycard.Project.Network.API_KEY
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun RecipesValuesScreen(navController: NavController, cardViewModel: CardViewModel, obj : MainActivity){
    Scaffold(topBar = { TopAppBarRecipesValues(navController = navController) }) {
        EditTexts(cardViewModel, obj, navController)
    }
}

@Composable
fun TopAppBarRecipesValues(navController: NavController){
    val buttonMode : Color
    val iconMode = Color.White
    if (isSystemInDarkTheme()){
        buttonMode = Color(39,39,39)
    }else{
        buttonMode = Color(98,0,238)
    }
    TopAppBar(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = { navController.navigate(route = Screens.RecipesSearch.route) }, colors = ButtonDefaults.buttonColors(backgroundColor = buttonMode)) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "SettingsRecipes", tint = iconMode)
        }
        Text(text = "Recipes Values", fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.h5.fontSize)
        Spacer(Modifier.weight(1f, true))

    }

}

@Composable
fun EditTexts(viewModel: CardViewModel, obj : MainActivity, navController: NavController){

    var text : String by remember { mutableStateOf("") }
    var cuisine : String by remember { mutableStateOf("") }
    var diet : String by remember { mutableStateOf("") }
    var intolerances : String by remember { mutableStateOf("") }
    var equipment : String by remember { mutableStateOf("") }
    var includeIngredients : String by remember { mutableStateOf("") }
    var excludeIngredients : String by remember { mutableStateOf("") }
    var type : String by remember { mutableStateOf("") }
    var instructionsRequired : Boolean by remember { mutableStateOf(false) }
    var addRecipeInformation : Boolean by remember { mutableStateOf(false) }
    var titleMatch : String by remember { mutableStateOf("") }
    var maxReadyTime : String by remember { mutableStateOf("") }
    var minCarbs : String by remember { mutableStateOf("") }
    var maxCarbs : String by remember { mutableStateOf("") }
    var minProtein : String by remember { mutableStateOf("") }
    var maxProtein : String by remember { mutableStateOf("") }
    var minCalories : String by remember { mutableStateOf("") }
    var maxCalories : String by remember { mutableStateOf("") }
    var minFat : String by remember { mutableStateOf("") }
    var maxFat : String by remember { mutableStateOf("") }
    var minSugar : String by remember { mutableStateOf("") }
    var maxSugar : String by remember { mutableStateOf("") }

    Box(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedTextField(value =  text, onValueChange = {
                text = it
            }, label = { Text(text = "Name")})
            OutlinedTextField(value = cuisine, onValueChange = {
                cuisine = it
            }, label = { Text(text = "Cuisine")})
            OutlinedTextField(value =  diet, onValueChange = {
                diet = it
            }, label = { Text(text = "Diet")})
            OutlinedTextField(value =  intolerances, onValueChange = {
                intolerances = it
            }, label = { Text(text = "Intolerances")})
            OutlinedTextField(value =  equipment, onValueChange = {
                equipment = it
            }, label = { Text(text = "Equipment")})
            OutlinedTextField(value =  includeIngredients, onValueChange = {
                includeIngredients = it
            }, label = { Text(text = "Include ingredients")})
            OutlinedTextField(value =  excludeIngredients, onValueChange = {
                excludeIngredients = it
            }, label = { Text(text = "Exclude Ingredients")})
            OutlinedTextField(value =  type, onValueChange = {
                type = it
            }, label = { Text(text = "Type")})
            OutlinedTextField(value =  titleMatch, onValueChange = {
                titleMatch = it
            }, label = { Text(text = "Title match")})
            OutlinedTextField(value =  maxReadyTime, onValueChange = {
                maxReadyTime = it
            }, label = { Text(text = "Ready time")})
            OutlinedTextField(value =  minCarbs, onValueChange = {
                minCarbs = it
            }, label = { Text(text = "Min Carbs")})
            OutlinedTextField(value =  maxCarbs, onValueChange = {
                maxCarbs = it
            }, label = { Text(text = "Max Carbs")})
            OutlinedTextField(value =  minProtein, onValueChange = {
                minProtein = it
            }, label = { Text(text = "Min Protein")})
            OutlinedTextField(value =  maxProtein, onValueChange = {
                maxProtein = it
            }, label = { Text(text = "Max Protein")})
            OutlinedTextField(value =  minCalories, onValueChange = {
                minCalories = it
            }, label = { Text(text = "Min Calories")})
            OutlinedTextField(value =  maxCalories, onValueChange = {
                maxCalories = it
            }, label = { Text(text = "Max Calories")})
            OutlinedTextField(value =  minFat, onValueChange = {
                minFat = it
            }, label = { Text(text = "Min Fat")})
            OutlinedTextField(value =  maxFat, onValueChange = {
                maxFat = it
            }, label = { Text(text = "Max Fat")})
            OutlinedTextField(value =  minSugar, onValueChange = {
                minSugar = it
            }, label = { Text(text = "Min Sugar")})
            OutlinedTextField(value = maxSugar, onValueChange = {
                maxSugar = it
            }, label = { Text(text = "Max Sugar")})

            Row(Modifier.padding(vertical = 10.dp)) {
                Switch(checked = instructionsRequired, onCheckedChange = {instructionsRequired = it})
                Text(text = "Instructions")
                Switch(checked = addRecipeInformation, onCheckedChange = {addRecipeInformation = it})
                Text(text = "Recipe Info")
            }
            Button(onClick = {
                try {
                    viewModel.getRecipesApi(
                        API_KEY,
                        text,
                        (if(cuisine.isEmpty()) null else cuisine),
                        (if(diet.isEmpty()) null else diet),
                        (if(intolerances.isEmpty()) null else intolerances),
                        (if(equipment.isEmpty()) null else equipment),
                        (if(includeIngredients.isEmpty()) null else includeIngredients),
                        (if(excludeIngredients.isEmpty()) null else excludeIngredients),
                        (if(type.isEmpty()) null else type),
                        instructionsRequired,
                        addRecipeInformation,
                        (if(titleMatch.isEmpty()) null else titleMatch),
                        (if(maxReadyTime.isEmpty()) null else maxReadyTime),
                        (if(minCarbs.isEmpty()) null else minCarbs),
                        (if(maxCarbs.isEmpty()) null else maxCarbs),
                        (if(minProtein.isEmpty()) null else minProtein),
                        (if(maxProtein.isEmpty()) null else maxProtein),
                        (if(minCalories.isEmpty()) null else minCalories),
                        (if(maxCalories.isEmpty()) null else maxCalories),
                        (if(minFat.isEmpty()) null else minFat),
                        (if(maxFat.isEmpty()) null else maxFat),
                        (if(minSugar.isEmpty()) null else minSugar),
                        (if(maxSugar.isEmpty()) null else maxSugar),
                        "5"
                    )

                    viewModel.recipesResponse.observe(obj, Observer {
                        if(it.isSuccessful){
                            val encodedObject = URLEncoder.encode(it.body().toString(), StandardCharsets.UTF_8.toString())
                            navController.navigate(route = Screens.RecipesSearch.passObject(encodedObject))
                        }
                    })
                }catch (exception : Exception){
                    Log.e(exception.toString(), "CANT_FETCH_DATA")
                }
            }) {
                Text(text = "Search")
            }
        }
    }
}