package com.example.mycard.Project.MVVM.View.Screens

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mycard.Project.MVVM.Models.CardModel
import com.example.mycard.Project.MVVM.Models.FavoriteRecipesModel
import com.example.mycard.Project.MVVM.ViewModels.FavoriteRecipesViewModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@ExperimentalMaterialApi
@Composable
fun FavoriteScreen(navController: NavController, favoriteRecipesViewModel: FavoriteRecipesViewModel){
    val getAllRecipesVM = favoriteRecipesViewModel.getAllRecipes.collectAsState(initial = listOf()).value

    Scaffold(topBar = { TopAppBarFavoriteRecipes(navController = navController)}) {
        CustomLazyColumnFavoriteItem(list = getAllRecipesVM, favoriteRecipesViewModel = favoriteRecipesViewModel)
    }
}

@Composable
fun TopAppBarFavoriteRecipes(navController: NavController){
    val buttonMode : Color
    val iconMode = Color.White
    if (isSystemInDarkTheme()){
        buttonMode = Color(39,39,39)
    }else{
        buttonMode = Color(98,0,238)
    }
    TopAppBar(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = {
            navController.navigate(Screens.RecipesSearch.route)
        }, colors = ButtonDefaults.buttonColors(backgroundColor = buttonMode)) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Search Recipes", tint = iconMode)
        }
        Text(text = "Favorite Recipes", fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.h5.fontSize)
    }
}

@ExperimentalMaterialApi
@Composable
fun CustomLazyColumnFavoriteItem(list : List<FavoriteRecipesModel>, favoriteRecipesViewModel: FavoriteRecipesViewModel){

    val backgroundMode : Color
    if (isSystemInDarkTheme()){
        backgroundMode = Color(20,20,20)
    }else{
        backgroundMode = Color.White
    }

    LazyColumn(contentPadding = PaddingValues(vertical = 10.dp, horizontal = 5.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(list) { item ->
            //swipe to delete

            val archive = SwipeAction(
                onSwipe = {
                    favoriteRecipesViewModel.deleteFromFavoriteRecipes(FavoriteRecipesModel(item.id, "", emptyList()))
                },
                icon = {Icon(imageVector = Icons.Default.Delete, contentDescription = "DeleteOnSwipe", modifier = Modifier.padding(16.dp), tint = Color.White)},
                background = Color.Red,
            )
            SwipeableActionsBox(endActions = listOf(archive), backgroundUntilSwipeThreshold = Color.Green, swipeThreshold = 200.dp) {
                ListItem(
                    modifier = Modifier
                        .border(2.dp, color = Color.Green, shape = RoundedCornerShape(15.dp))
                        .background(backgroundMode),
                    text = { Text(text = item.title, fontSize = MaterialTheme.typography.h6.fontSize) },
                    secondaryText = {
                        Column() {
                            item.nutrients.forEach {
                                Column() {
                                    Row() {
                                        Text(text = it.name + " ")
                                        Text(text = it.amount.toString() + " ")
                                        Text(text = it.unit + ". ")
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}