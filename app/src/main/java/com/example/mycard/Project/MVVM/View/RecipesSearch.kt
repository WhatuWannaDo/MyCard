package com.example.mycard.Project.MVVM.View

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.example.mycard.MainActivity
import com.example.mycard.Project.MVVM.Models.FavoriteRecipesModel
import com.example.mycard.Project.MVVM.Models.NutrientsFavoriteModel
import com.example.mycard.Project.MVVM.View.Screens.Screens
import com.example.mycard.Project.MVVM.ViewModels.CardViewModel
import com.example.mycard.Project.MVVM.ViewModels.FavoriteRecipesViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.util.ArrayList

@ExperimentalMaterialApi
@Composable
fun RecipesSearchScreen(navController: NavController, responseObject : String, viewModel: CardViewModel, obj : MainActivity, favoriteRecipesViewModel: FavoriteRecipesViewModel){
    //decode received url and call the method which will call this url to api again
    val urlDecodedObject = URLDecoder.decode(responseObject, StandardCharsets.UTF_8.toString())
    viewModel.getByURL(urlDecodedObject)
    Scaffold(topBar = { TopAppBarRecipes(navController = navController) }) {
        CustomLazyColumnRecipesItem(viewModel = viewModel, obj = obj, favoriteRecipesViewModel)
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
        Button(onClick = {
            navController.navigate(Screens.RecipesSearchValues.route)
        }, colors = ButtonDefaults.buttonColors(backgroundColor = buttonMode)) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Recipes", tint = iconMode)
        }
        Button(onClick = {
            navController.navigate(Screens.FavoriteRecipes.route)
        }, colors = ButtonDefaults.buttonColors(backgroundColor = buttonMode)) {
            Icon(imageVector = Icons.Default.Favorite, contentDescription = "SettingsRecipes", tint = iconMode)
        }
        Button(onClick = { navController.navigate(route = Screens.Settings.route) }, colors = ButtonDefaults.buttonColors(backgroundColor = buttonMode)) {
            Icon(imageVector = Icons.Default.Settings, contentDescription = "SettingsRecipes", tint = iconMode)
        }
    }

}

@SuppressLint("CommitPrefEdits", "UnrememberedMutableState")
@ExperimentalMaterialApi
@Composable
fun CustomLazyColumnRecipesItem(viewModel: CardViewModel, obj : MainActivity, favoriteRecipesViewModel: FavoriteRecipesViewModel) {
    val recompositionState = mutableStateOf(0) //increased automatically for recomposition (should be removed and changed for smth else!!!)

    val backgroundMode : Color
    if (isSystemInDarkTheme()){
        backgroundMode = Color(20,20,20)
    }else{
        backgroundMode = Color.White
    }
    LazyColumn(contentPadding = PaddingValues(vertical = 10.dp, horizontal = 5.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        viewModel.urlResponse.observe(obj, Observer {response ->
            recompositionState.value += 1
            if(response.isSuccessful) {
                items(response.body()?.results!!){
                    ListItem(
                        modifier = Modifier
                            .border(2.dp, color = Color.Red, shape = RoundedCornerShape(15.dp))
                            .background(backgroundMode),
                        text = { Text(text = it.title, fontSize = MaterialTheme.typography.h6.fontSize) },
                        secondaryText = {
                            Column() {
                                it.nutrition?.nutrients?.forEach {
                                    Column() {
                                        Row() {
                                            Text(text = it.name + " ")
                                            Text(text = it.amount.toString() + " ")
                                            Text(text = it.unit + ". ")
                                        }
                                    }
                                }
                            }
                        },
                        trailing = { Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "AddToFavoriteRecipes", modifier = Modifier.clickable {
                            favoriteRecipesViewModel.insertIntoFavoriteRecipes(FavoriteRecipesModel(0, it.title, it.nutrition?.nutrients as List<NutrientsFavoriteModel>))
                        })}
                    )
                }
            }else{
                when(response.code()){
                    500 -> Toast.makeText(obj, "Server error", Toast.LENGTH_SHORT).show()
                    423 -> Toast.makeText(obj, "Server blocked", Toast.LENGTH_SHORT).show()
                    410 -> Toast.makeText(obj, "The server isn't exist", Toast.LENGTH_SHORT).show()
                    400 -> Toast.makeText(obj, "Can't validate call", Toast.LENGTH_SHORT).show()
                    401 -> Toast.makeText(obj, "Non authorized", Toast.LENGTH_SHORT).show()
                    402 -> Toast.makeText(obj, "Limited", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
