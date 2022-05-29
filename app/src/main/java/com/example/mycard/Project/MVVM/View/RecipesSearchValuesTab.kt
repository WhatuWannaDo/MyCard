package com.example.mycard.Project.MVVM.View

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.example.mycard.MainActivity
import com.example.mycard.Project.MVVM.View.Screens.Screens
import com.example.mycard.Project.MVVM.ViewModels.CardViewModel
import com.example.mycard.Project.Network.API_KEY
import kotlinx.coroutines.*
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@SuppressLint("UnrememberedMutableState")
@DelicateCoroutinesApi
@Composable
fun RecipesValuesScreen(navController: NavController, cardViewModel: CardViewModel, obj : MainActivity){
    val progressState = mutableStateOf(false)

    Scaffold(topBar = { TopAppBarRecipesValues(navController = navController) }) {
        EditTexts(cardViewModel, obj, navController, progressState)
        ProgressBarCircular(progressState = progressState, delayInMillis = 5000)
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

@DelicateCoroutinesApi
@Composable
fun EditTexts(viewModel: CardViewModel, obj : MainActivity, navController: NavController, progressState : MutableState<Boolean>){

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

    val dropDownMenuCuisine = listOf(
        "African",
        "American",
        "British",
        "Cajun",
        "Caribbean",
        "Chinese",
        "Eastern European",
        "European",
        "French",
        "German",
        "Greek",
        "Indian",
        "Irish",
        "Italian",
        "Japanese",
        "Jewish",
        "Korean",
        "Latin American",
        "Mediterranean",
        "Mexican",
        "Middle Eastern",
        "Nordic",
        "Southern",
        "Spanish",
        "Thai",
        "Vietnamese"
    )
    var dropDownMenuCuisineState : Boolean by remember { mutableStateOf(false) }

    val dropDownMenuIntolerances = listOf(
        "Dairy",
        "Egg",
        "Gluten",
        "Grain",
        "Peanut",
        "Seafood",
        "Sesame",
        "Shellfish",
        "Soy",
        "Sulfite",
        "Tree Nut",
        "Wheat"
    )
    var dropDownMenuIntolerancesState : Boolean by remember { mutableStateOf(false) }

    val dropDownMenuDiet = listOf(
        "Gluten Free",
        "Ketogenic",
        "Vegetarian",
        "Lacto-Vegetarian",
        "Ovo-Vegetarian",
        "Vegan",
        "Pescetarian",
        "Paleo",
        "Primal",
        "Low FODMAP",
        "Whole30"
    )
    var dropDownMenuDietState : Boolean by remember { mutableStateOf(false) }

    val dropDownMenuMealTypes = listOf(
        "Main course",
        "Side dish",
        "Dessert",
        "Appetizer",
        "Salad",
        "Bread",
        "Breakfast",
        "Soup",
        "Beverage",
        "Sauce",
        "Marinade",
        "Fingerfood",
        "Snack",
        "Drink"
    )
    var dropDownMenuMealTypesState : Boolean by remember { mutableStateOf(false) }

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


            Box {
                OutlinedTextField(value = cuisine, onValueChange = {
                    cuisine = it
                }, label = { Text(text = "Cuisine")}, readOnly = true, trailingIcon = { Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "DropDownMenuCuisine",
                    modifier = Modifier.clickable { dropDownMenuCuisineState = true }
                )}
                )
                DropdownMenu(
                    expanded = dropDownMenuCuisineState,
                    offset = DpOffset(x = 70.dp, 0.dp),
                    modifier = Modifier.size(width = 200.dp,height = 200.dp),
                    onDismissRequest = { dropDownMenuCuisineState = false }) {

                    dropDownMenuCuisine.forEach { label ->
                        DropdownMenuItem(onClick = {
                            cuisine = label
                            dropDownMenuCuisineState = false
                        }) {
                            Text(text = label)
                        }
                    }
                }
            }

            Box {
                OutlinedTextField(value =  diet, onValueChange = {
                    diet = it
                }, label = { Text(text = "Diet")}, readOnly = true, trailingIcon = { Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "DropDownMenuDiet",
                    modifier = Modifier.clickable { dropDownMenuDietState = true }
                )}
                )
                DropdownMenu(
                    expanded = dropDownMenuDietState,
                    offset = DpOffset(x = 70.dp, 0.dp),
                    modifier = Modifier.size(width = 200.dp,height = 200.dp),
                    onDismissRequest = { dropDownMenuDietState = false }) {

                    dropDownMenuDiet.forEach { label ->
                        DropdownMenuItem(onClick = {
                            diet = label
                            dropDownMenuDietState = false
                        }) {
                            Text(text = label)
                        }
                    }
                }
            }


            Box {
                OutlinedTextField(value =  intolerances, onValueChange = {
                    intolerances = it
                }, label = { Text(text = "Intolerances")}, readOnly = true, trailingIcon = { Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "DropDownMenuIntolerances",
                    modifier = Modifier.clickable { dropDownMenuIntolerancesState = true }
                )}
                )
                DropdownMenu(
                    expanded = dropDownMenuIntolerancesState,
                    offset = DpOffset(x = 70.dp, 0.dp),
                    modifier = Modifier.size(width = 200.dp,height = 200.dp),
                    onDismissRequest = { dropDownMenuIntolerancesState = false }) {

                    dropDownMenuIntolerances.forEach { label ->
                        DropdownMenuItem(onClick = {
                            intolerances = label
                            dropDownMenuIntolerancesState = false
                        }) {
                            Text(text = label)
                        }
                    }
                }
            }


            OutlinedTextField(value =  equipment, onValueChange = {
                equipment = it
            }, label = { Text(text = "Equipment (example - pan)")})

            OutlinedTextField(value =  includeIngredients, onValueChange = {
                includeIngredients = it
            }, label = { Text(text = "Include ingredients")})

            OutlinedTextField(value =  excludeIngredients, onValueChange = {
                excludeIngredients = it
            }, label = { Text(text = "Exclude Ingredients")})


            Box {
                OutlinedTextField(value =  type, onValueChange = {
                    type = it
                }, label = { Text(text = "Type")}, readOnly = true, trailingIcon = { Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "DropDownMenuMealType",
                    modifier = Modifier.clickable { dropDownMenuMealTypesState = true }
                )})
                DropdownMenu(
                    expanded = dropDownMenuMealTypesState,
                    offset = DpOffset(x = 70.dp, 0.dp),
                    modifier = Modifier.size(width = 200.dp,height = 200.dp),
                    onDismissRequest = { dropDownMenuMealTypesState = false }) {

                    dropDownMenuMealTypes.forEach { label ->
                        DropdownMenuItem(onClick = {
                            type = label
                            dropDownMenuMealTypesState = false
                        }) {
                            Text(text = label)
                        }
                    }
                }
            }


            OutlinedTextField(value =  titleMatch, onValueChange = {
                titleMatch = it
            }, label = { Text(text = "Title match")})

            OutlinedTextField(value =  maxReadyTime,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                maxReadyTime = it
            }, label = { Text(text = "Ready time")})

            OutlinedTextField(value =  minCarbs,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                minCarbs = it
            }, label = { Text(text = "Min Carbs")})

            OutlinedTextField(value =  maxCarbs,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                maxCarbs = it
            }, label = { Text(text = "Max Carbs")})

            OutlinedTextField(value =  minProtein,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                minProtein = it
            }, label = { Text(text = "Min Protein")})

            OutlinedTextField(value =  maxProtein,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                maxProtein = it
            }, label = { Text(text = "Max Protein")})

            OutlinedTextField(value =  minCalories,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                minCalories = it
            }, label = { Text(text = "Min Calories")})

            OutlinedTextField(value =  maxCalories,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                maxCalories = it
            }, label = { Text(text = "Max Calories")})

            OutlinedTextField(value =  minFat,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                minFat = it
            }, label = { Text(text = "Min Fat")})

            OutlinedTextField(value =  maxFat,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                maxFat = it
            }, label = { Text(text = "Max Fat")})

            OutlinedTextField(value =  minSugar,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                minSugar = it
            }, label = { Text(text = "Min Sugar")})

            OutlinedTextField(value = maxSugar,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                maxSugar = it
            }, label = { Text(text = "Max Sugar")})
/*
would be added soon
            Row(Modifier.padding(vertical = 10.dp)) {
                Switch(checked = instructionsRequired, onCheckedChange = {instructionsRequired = it})
                Text(text = "Instructions")
                Switch(checked = addRecipeInformation, onCheckedChange = {addRecipeInformation = it})
                Text(text = "Recipe Info")
            }*/
            Button(onClick = {
                try {
                    if(text.isNullOrEmpty()){
                        Toast.makeText(obj, "Fill the name field!", Toast.LENGTH_SHORT).show()
                    }else{

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
                            //instructionsRequired,
                            //addRecipeInformation,
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
                                progressState.value = true
                                //receive url from received object and send it by navigation
                                val encodedObject = URLEncoder.encode(it.raw().request.url.toString(), StandardCharsets.UTF_8.toString())
                                GlobalScope.launch(Dispatchers.Main) {
                                    delay(5000)
                                    navController.navigate(route = Screens.RecipesSearch.passObject(encodedObject))

                                }
                            }
                        })
                    }
                }catch (exception : Exception){
                    Log.e(exception.toString(), "CANT_FETCH_DATA")
                }
            }) {
                Text(text = "Search")
            }
        }
    }
}



