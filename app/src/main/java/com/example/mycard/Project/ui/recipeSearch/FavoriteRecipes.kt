package com.example.mycard.Project.MVVM.View.Screens

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.mycard.Project.ui.MainActivity
import com.example.mycard.Project.MVVM.Models.FavoriteRecipesModel
import com.example.mycard.Project.MVVM.View.NoDataText
import com.example.mycard.Project.ui.screens.Screens
import com.example.mycard.Project.ui.viewModels.FavoriteRecipesViewModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@SuppressLint("UnrememberedMutableState")
@ExperimentalMaterialApi
@Composable
fun FavoriteScreen(navController: NavController, favoriteRecipesViewModel: FavoriteRecipesViewModel, obj: MainActivity){
    val getAllRecipesVM = favoriteRecipesViewModel.getAllRecipes.collectAsState(initial = listOf()).value
    val noDataState = mutableStateOf(true)

    Scaffold(topBar = { TopAppBarFavoriteRecipes(navController = navController)}) {
        CustomLazyColumnFavoriteItem(list = getAllRecipesVM, favoriteRecipesViewModel = favoriteRecipesViewModel, obj = obj, noDataState)
        NoDataText(noDataState = noDataState)
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
fun CustomLazyColumnFavoriteItem(list : List<FavoriteRecipesModel>, favoriteRecipesViewModel: FavoriteRecipesViewModel, obj : MainActivity, noDataState : MutableState<Boolean>){

    val backgroundMode : Color
    if (isSystemInDarkTheme()){
        backgroundMode = Color(20,20,20)
    }else{
        backgroundMode = Color.White
    }
    if(list.isNotEmpty()){
        noDataState.value = false
    }

    LazyColumn(contentPadding = PaddingValues(vertical = 10.dp, horizontal = 5.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(list) { item ->
            //swipe to delete

            val archive = SwipeAction(
                onSwipe = {
                    favoriteRecipesViewModel.deleteFromFavoriteRecipes(FavoriteRecipesModel(item.id, title = "", image = "", recipeUrl = "" ,nutrients = emptyList()))
                },
                icon = {Icon(imageVector = Icons.Default.Delete, contentDescription = "DeleteOnSwipe", modifier = Modifier.padding(16.dp), tint = Color.White)},
                background = Color.Red,
            )
            SwipeableActionsBox(endActions = listOf(archive), backgroundUntilSwipeThreshold = Color.Green, swipeThreshold = 200.dp) {
                ListItem(
                    modifier = Modifier
                        .border(2.dp, color = Color.Red, shape = RoundedCornerShape(15.dp))
                        .background(backgroundMode),
                    text = { Text(text = item.title, fontSize = MaterialTheme.typography.h6.fontSize) },
                    secondaryText = {
                        Column() {
                            item.nutrients?.forEach {
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
                    icon = {
                        Column() {
                            Image(
                                painter = rememberAsyncImagePainter(item.image),
                                contentDescription = null,
                                modifier = Modifier.size(128.dp)
                            )
                            Text(text = "More info...", color = Color(61, 91, 141), modifier = Modifier.clickable {
                                obj.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.recipeUrl)))
                            })
                        }
                    }
                )
            }
        }
    }
}