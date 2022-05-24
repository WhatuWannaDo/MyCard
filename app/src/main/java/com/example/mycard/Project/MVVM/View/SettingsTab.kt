package com.example.mycard.Project.MVVM.View

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mycard.MainActivity
import com.example.mycard.Project.MVVM.View.NavGraph.getDest
import kotlinx.coroutines.*

@DelicateCoroutinesApi
@SuppressLint("UnrememberedMutableState")
@ExperimentalMaterialApi
@Composable
fun SettingsTab(navController: NavController, obj : MainActivity){
    val sharedPrefs : SharedPreferences = obj.getSharedPreferences("Category", MODE_PRIVATE)
    val progressState = mutableStateOf(false)

    Scaffold(topBar = { TopAppBarSettings(navController = navController, obj = obj)}) {
        ColumnSettings(sharedPrefs = sharedPrefs, progressState = progressState)
        ProgressBarCircular(progressState = progressState)
    }


}
@Composable
fun TopAppBarSettings(navController: NavController, obj: MainActivity){
    TopAppBar(Modifier.fillMaxWidth()) {
        Button(onClick = {  navController.navigate(route = getDest(obj = obj))  }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "BackToMain", modifier = Modifier.size(26.dp))
        }
        Text(text = "Settings", fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.h5.fontSize)
    }
}
@SuppressLint("UnrememberedMutableState")
@ExperimentalMaterialApi
@Composable
fun ColumnSettings(sharedPrefs : SharedPreferences, progressState : MutableState<Boolean>){
    val searchOptions = listOf("Recipes Search","Ingredient Search","Grocery Products Search", "Menu Items Search")
    val (selectedSearchButtonOptions, onSearchButtonOptionsSelected) = remember { mutableStateOf(searchOptions[0]) }
    val editor = sharedPrefs.edit()
    Column(Modifier.selectableGroup()) {
        searchOptions.forEach {
            Row() {
                RadioButton(
                    selected = it == selectedSearchButtonOptions,
                    onClick = {
                        onSearchButtonOptionsSelected(it)
                        progressState.value = true
                        editor.putString("SelectedCategory", it).apply()
                              })
                Text(text = it, modifier = Modifier.clickable {
                    onSearchButtonOptionsSelected(it)
                })
            }
        }

    }
}
@DelicateCoroutinesApi
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ProgressBarCircular(progressState : MutableState<Boolean>){
    if(progressState.value){
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            CircularProgressIndicator()
            GlobalScope.launch(Dispatchers.Default) {
                delay(2000)
                progressState.value = false
            }
        }
    }
}
