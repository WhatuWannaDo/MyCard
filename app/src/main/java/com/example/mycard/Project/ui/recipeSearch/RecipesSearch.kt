package com.example.mycard.Project.MVVM.View

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.*
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.mycard.Project.ui.MainActivity
import com.example.mycard.Project.MVVM.Models.FavoriteRecipesModel
import com.example.mycard.Project.MVVM.Models.NutrientsFavoriteModel
import com.example.mycard.Project.data.models.databaseModels.CardModel
import com.example.mycard.Project.data.models.databaseModels.FolderModel
import com.example.mycard.Project.ui.screens.Screens
import com.example.mycard.Project.ui.viewModels.CardViewModel
import com.example.mycard.Project.ui.viewModels.FavoriteRecipesViewModel
import com.example.mycard.Project.ui.viewModels.FolderViewModel
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@DelicateCoroutinesApi
@SuppressLint("UnrememberedMutableState")
@ExperimentalMaterialApi
@Composable
fun RecipesSearchScreen(
    navController: NavController,
    responseObject : String,
    viewModel: CardViewModel,
    obj : MainActivity,
    favoriteRecipesViewModel: FavoriteRecipesViewModel,
    cardViewModel: CardViewModel,
    folderViewModel: FolderViewModel
){
    //decode received url and call the method which will call this url to api again
    val urlDecodedObject = URLDecoder.decode(responseObject, StandardCharsets.UTF_8.toString())
    viewModel.getByURL(urlDecodedObject)

    val noDataState = mutableStateOf(true)
    Scaffold(topBar = { TopAppBarRecipes(navController = navController) }) {
        CustomLazyColumnRecipesItem(viewModel = viewModel, obj = obj, favoriteRecipesViewModel, noDataState, cardViewModel, folderViewModel)
        NoDataText(noDataState = noDataState)
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

@DelicateCoroutinesApi
@SuppressLint("CommitPrefEdits", "UnrememberedMutableState")
@ExperimentalMaterialApi
@Composable
fun CustomLazyColumnRecipesItem(
    viewModel: CardViewModel,
    obj : MainActivity,
    favoriteRecipesViewModel: FavoriteRecipesViewModel,
    noDataState : MutableState<Boolean>,
    cardViewModel: CardViewModel,
    folderViewModel: FolderViewModel
) {
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

                if (response.body()?.results?.size != 0){
                    noDataState.value = false
                }

                items(response.body()?.results!!){item ->
                    ListItem(
                        modifier = Modifier
                            .border(2.dp, color = Color.Red, shape = RoundedCornerShape(15.dp))
                            .background(backgroundMode),
                        text = {
                            Column(modifier = Modifier.fillMaxSize()) {
                                Text(text = item.title, fontSize = MaterialTheme.typography.h6.fontSize)
                                Box(contentAlignment = Alignment.BottomEnd){
                                    Button(onClick = {
                                        val listOfRecipes = ArrayList<CardModel>()
                                        item.analyzedInstructions.forEach { instr ->
                                            instr.steps.forEach { steps ->
                                                steps.ingredients.forEach { analyzedIngredients ->
                                                    listOfRecipes.add(CardModel(0, analyzedIngredients.name, "1", ""))
                                                }
                                            }

                                        }
                                        GlobalScope.launch(Dispatchers.IO) {
                                            folderViewModel.addFolder(FolderModel(0, item.title, Gson().toJson(listOfRecipes)))
                                        }
                                        Toast.makeText(obj, "Ingredients added to ${item.title} folder into Ingredients tab", Toast.LENGTH_SHORT).show()
                                    }) {
                                        Text(text = "Get ingredients")
                                    }
                                }
                            }
                               },
                        secondaryText = {
                            Column() {
                                item.nutrition?.nutrients?.forEach { nutrition ->
                                    Column() {
                                        Row() {
                                            Text(text = nutrition.name + " ")
                                            Text(text = nutrition.amount.toString() + " ")
                                            Text(text = nutrition.unit + ". ")
                                        }
                                    }
                                }
                            }
                        },
                        trailing = { Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "AddToFavoriteRecipes", modifier = Modifier.clickable {
                            favoriteRecipesViewModel.insertIntoFavoriteRecipes(FavoriteRecipesModel(0, item.title, item.image, item.sourceUrl, item.nutrition?.nutrients as? List<NutrientsFavoriteModel>))
                        })},
                        icon = {
                            Column() {
                                Image(
                                    painter = rememberAsyncImagePainter(item.image),
                                    contentDescription = null,
                                    modifier = Modifier.size(128.dp)
                                )
                                Text(text = "More info...", color = Color(61, 91, 141), modifier = Modifier.clickable {
                                    obj.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.sourceUrl)))
                                })
                            }
                        }
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

@Composable
fun NoDataText(noDataState : MutableState<Boolean>){
    if (noDataState.value){
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
            Text(text = "No data here.", fontSize = MaterialTheme.typography.h6.fontSize)
        }
    }
}
