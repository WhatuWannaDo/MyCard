package com.example.mycard.Project.MVVM.View

import android.annotation.SuppressLint
import android.content.Context
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
import com.example.mycard.Project.MVVM.View.Screens.Screens
import kotlinx.coroutines.*

@DelicateCoroutinesApi
@SuppressLint("UnrememberedMutableState")
@ExperimentalMaterialApi
@Composable
fun SettingsTab(navController: NavController, obj : MainActivity){
    val sharedPrefs : SharedPreferences = obj.getSharedPreferences("Category", MODE_PRIVATE)
    val progressState = mutableStateOf(false)

    Scaffold(topBar = { TopAppBarSettings(navController = navController, obj = obj)}) {
        ColumnSettings(sharedPrefs = sharedPrefs, progressState = progressState, obj)
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
fun ColumnSettings(sharedPrefs : SharedPreferences, progressState : MutableState<Boolean>, obj: MainActivity){
    val searchOptions = listOf("Recipes Search","Ingredient Search","Grocery Products Search", "Menu Items Search")
    val (selectedSearchButtonOptions, onSearchButtonOptionsSelected) = remember { mutableStateOf(searchOptions[getRadioButtonNumber(obj)]) }
    val editor = sharedPrefs.edit()
    
    // radio buttons group
    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .selectableGroup()
                .fillMaxSize(), verticalArrangement = Arrangement.Center) {
            searchOptions.forEach {
                Row() {
                    Spacer(modifier = Modifier.height(40.dp))
                    RadioButton(
                        selected = it == selectedSearchButtonOptions,
                        onClick = {
                            onSearchButtonOptionsSelected(it)
                            progressState.value = true
                            editor.putString("SelectedCategory", it).apply()
                        })
                    Text(text = it, fontSize = MaterialTheme.typography.h6.fontSize, modifier = Modifier.clickable {
                        onSearchButtonOptionsSelected(it)
                        progressState.value = true
                        editor.putString("SelectedCategory", it).apply()
                    },
                    )
                }
            }

        }
    }
}
@DelicateCoroutinesApi
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ProgressBarCircular(progressState : MutableState<Boolean>){
    //progress bar creating after choosing some radio button. Thats need for time to write shared prefs into a memory
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



fun getRadioButtonNumber(obj: MainActivity) : Int {
    val sharedPrefsSettings : SharedPreferences = obj.getSharedPreferences("Category", Context.MODE_PRIVATE)
    when(sharedPrefsSettings.getString("SelectedCategory", "No Data").toString()){
        "Recipes Search" -> return 0
        "Ingredient Search" -> return 1
        "Grocery Products Search" -> return 2
        "Menu Items Search" -> return 3
    }
    return -1
}
