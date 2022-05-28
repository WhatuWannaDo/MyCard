package com.example.mycard.Project.MVVM.View

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
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

@ExperimentalMaterialApi
@Composable
fun GroceryProductsSearchScreen(navController: NavController, viewModel: CardViewModel, obj: MainActivity){
    var searchState : String by remember { mutableStateOf("") }
    Scaffold(topBar = { TopAppBarGrocery(navController = navController) }) {
        Column() {
            OutlinedTextField(
                value = searchState,
                onValueChange = {
                    searchState = it
                    viewModel.getGroceryApi(API_KEY, searchState, "30")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                label = {Text("Search")},
                singleLine = true
            )
            CustomLazyColumnGroceryItem(viewModel, obj)
        }
    }
}

@Composable
fun TopAppBarGrocery(navController: NavController){
    val buttonMode : Color
    if (isSystemInDarkTheme()){
        buttonMode = Color(39,39,39)
    }else{
        buttonMode = Color(98,0,238)
    }
    TopAppBar(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Grocery", fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.h5.fontSize)
        Spacer(Modifier.weight(1f, true))

        Button(onClick = { navController.navigate(route = Screens.Settings.route) }, colors = ButtonDefaults.buttonColors(backgroundColor = buttonMode)) {
            Icon(imageVector = Icons.Default.Settings, contentDescription = "SettingsGrocery")
        }
    }

}

@SuppressLint("CommitPrefEdits")
@ExperimentalMaterialApi
@Composable
fun CustomLazyColumnGroceryItem(viewModel: CardViewModel, obj : MainActivity) {
    val backgroundMode : Color
    val strokeMode : Color
    if (isSystemInDarkTheme()){
        backgroundMode = Color(20,20,20)
        strokeMode = Color.Yellow
    }else{
        backgroundMode = Color.White
        strokeMode = Color.Blue
    }
    LazyColumn(contentPadding = PaddingValues(vertical = 10.dp, horizontal = 5.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        viewModel.groceryResponse.observe(obj, Observer {response ->
            val result = response.body()?.products?.size
            if(response.isSuccessful) {
                result?.let {
                    items(response.body()?.products!!){
                        ListItem(
                            modifier = Modifier
                                .border(2.dp, color = strokeMode, shape = RoundedCornerShape(15.dp))
                                .background(backgroundMode),
                            text = { Text(text = it.title, fontSize = MaterialTheme.typography.h6.fontSize) }
                        )
                    }
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
