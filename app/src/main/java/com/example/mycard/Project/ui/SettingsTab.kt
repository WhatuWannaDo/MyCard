package com.example.mycard.Project.MVVM.View

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mycard.Project.ui.MainActivity
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
        ColumnSettings(sharedPrefs = sharedPrefs, progressState = progressState, obj)
        ProgressBarCircular(progressState = progressState, delayInMillis = 2000)
    }


}
@Composable
fun TopAppBarSettings(navController: NavController, obj: MainActivity){
    val buttonMode : Color
    val iconMode = Color.White
    if (isSystemInDarkTheme()){
        buttonMode = Color(39,39,39)
    }else{
        buttonMode = Color(98,0,238)
    }
    TopAppBar(Modifier.fillMaxWidth()) {
        Button(onClick = {  navController.navigate(route = getDest(obj = obj))  }, colors = ButtonDefaults.buttonColors(backgroundColor = buttonMode)) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "BackToMain", modifier = Modifier.size(26.dp), tint = iconMode)
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
    
    Column(
        Modifier
            .selectableGroup()
            .padding(vertical = 60.dp, horizontal = 20.dp),
    ) {
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
                })
            }
        }
    }
    Column(
        Modifier
            .padding(vertical = 20.dp, horizontal = 10.dp)) {
        Text(text = "Categories:", fontSize = MaterialTheme.typography.h5.fontSize)
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(vertical = 220.dp, horizontal = 10.dp)) {
        Text(text = "After select any category, \nwait until progress will finish", color = Color.DarkGray)
    }
}
@DelicateCoroutinesApi
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ProgressBarCircular(progressState : MutableState<Boolean>, delayInMillis : Long){
    //progress bar creating after choosing some radio button. Thats need for time to write shared prefs into a memory
    if(progressState.value){
        Column(
            Modifier
                .fillMaxSize()
                .padding(vertical = 60.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
            CircularProgressIndicator()
            GlobalScope.launch(Dispatchers.Default) {
                delay(delayInMillis)
                progressState.value = false
            }
        }
    }
}



fun getRadioButtonNumber(obj: MainActivity) : Int {
    val sharedPrefsSettings : SharedPreferences = obj.getSharedPreferences("Category", Context.MODE_PRIVATE)
    when(sharedPrefsSettings.getString("SelectedCategory", "Ingredient Search").toString()){
        "Recipes Search" -> return 0
        "Ingredient Search" -> return 1
        "Grocery Products Search" -> return 2
        "Menu Items Search" -> return 3
    }
    return -1
}

/*
    ключ - значение

 */