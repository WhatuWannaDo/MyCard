package com.example.mycard.Project.MVVM.View

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectableGroup

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mycard.Project.MVVM.View.Screens.Screens

@ExperimentalMaterialApi
@Composable
fun SettingsTab(navController: NavController){
    Scaffold(topBar = { TopAppBarSettings(navController = navController)}) {
        ColumnSettings()
    }

}
@Composable
fun TopAppBarSettings(navController: NavController){
    TopAppBar(Modifier.fillMaxWidth()) {
        Button(onClick = {  navController.navigate(route = Screens.Home.route)  }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "BackToMain", modifier = Modifier.size(26.dp))
        }
        Text(text = "Settings", fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.h5.fontSize)
    }
}
@SuppressLint("UnrememberedMutableState")
@ExperimentalMaterialApi
@Composable
fun ColumnSettings(){

    val searchOptions = listOf("Recipes Search","Ingredient Search","Grocery Products Search", "Menu Items Search")
    val (selectedSearchButtonOptions, onSearchButtonOptionsSelected) = remember { mutableStateOf(searchOptions[0]) }

    Column(Modifier.selectableGroup()) {
        searchOptions.forEach {
            Row() {
                RadioButton(
                    selected = it == selectedSearchButtonOptions,
                    onClick = { onSearchButtonOptionsSelected(it) },
                    enabled = true)
                Text(text = it, modifier = Modifier.clickable {
                    onSearchButtonOptionsSelected(it)
                })
            }
        }

    }
}