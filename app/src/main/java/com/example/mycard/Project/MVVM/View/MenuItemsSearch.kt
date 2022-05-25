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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
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
fun MenuItemSearchScreen(navController: NavController, viewModel: CardViewModel, obj: MainActivity){
    viewModel.getMenuItemsApi(API_KEY, "choco", "30")

    Scaffold(topBar = { TopAppBarMenu(navController = navController) }) {
        CustomLazyColumnMenuItem(viewModel, obj)
    }
}

@Composable
fun TopAppBarMenu(navController: NavController){
    val buttonMode : Color
    val iconMode = Color.White
    if (isSystemInDarkTheme()){
        buttonMode = Color(39,39,39)
    }else{
        buttonMode = Color(98,0,238)
    }
    TopAppBar(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Menu Items", fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.h5.fontSize)
        Spacer(Modifier.weight(1f, true))
        Icon(imageVector = Icons.Default.Search, contentDescription = "Search MenuItems", tint = iconMode, modifier = Modifier.clickable {
            //TODO: add search tab
        })
        Button(onClick = { navController.navigate(route = Screens.Settings.route) }, colors = ButtonDefaults.buttonColors(backgroundColor = buttonMode)) {
            Icon(imageVector = Icons.Default.Settings, contentDescription = "SettingsMenuItems", tint = iconMode)
        }
    }

}

@SuppressLint("CommitPrefEdits")
@ExperimentalMaterialApi
@Composable
fun CustomLazyColumnMenuItem(viewModel: CardViewModel, obj : MainActivity) {
    val backgroundMode : Color
    if (isSystemInDarkTheme()){
        backgroundMode = Color(20,20,20)
    }else{
        backgroundMode = Color.White
    }
    LazyColumn(contentPadding = PaddingValues(vertical = 10.dp, horizontal = 5.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        viewModel.menuItemsResponse.observe(obj, Observer {response ->
            val result = response.body()?.menuItems?.size
            if(response.isSuccessful) {
                result?.let {
                    items(response.body()?.menuItems!!){
                        ListItem(
                            modifier = Modifier
                                .border(2.dp, color = Color.Red, shape = RoundedCornerShape(15.dp))
                                .background(backgroundMode),
                            text = { Text(text = it.title, fontSize = MaterialTheme.typography.h6.fontSize) },
                            secondaryText = { Text(text = it.restaurantChain, fontSize = MaterialTheme.typography.h6.fontSize) }
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
