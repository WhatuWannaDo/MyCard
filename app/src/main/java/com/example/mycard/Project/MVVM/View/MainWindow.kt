package com.example.mycard.Project.MVVM.View

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.mycard.Project.MVVM.Models.CardModel
import com.example.mycard.Project.MVVM.ViewModels.CardViewModel
import com.example.mycard.Project.Room.Repository.CardRepository

@SuppressLint("UnrememberedMutableState")

@ExperimentalMaterialApi
@Composable
fun MainWindow(cardViewModel: CardViewModel){
    val getAllProductsVM = cardViewModel.getAllProducts.collectAsState(initial = listOf()).value
    val showDialog = mutableStateOf(false)

    Scaffold(topBar = {TopAppBarCard(cardViewModel = cardViewModel, showDialog = showDialog)}) {
        Box() {
            CustomLazyColumnItem(list = getAllProductsVM)
        }
    }
}

@Composable
fun TopAppBarCard(cardViewModel: CardViewModel, showDialog : MutableState<Boolean>){
    TopAppBar(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Продукты", fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.h5.fontSize)
        Spacer(Modifier.weight(1f, true))
        Button(onClick = {
            showDialog.value = true
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "AddProduct", modifier = Modifier.size(26.dp))
        }
        if(showDialog.value) {
            AddNewProduct(cardViewModel = cardViewModel, showDialog = showDialog)
        }

    }



}

@ExperimentalMaterialApi
@Composable
fun CustomLazyColumnItem(list : List<CardModel>) {
    LazyColumn(contentPadding = PaddingValues(vertical = 10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(list) { product ->
            ListItem(
                modifier = Modifier.background(Color.LightGray),
                text = { Text(text = product.productName, fontSize = MaterialTheme.typography.h6.fontSize) },
                trailing = { Text(text = "Количество: " + product.productAmount, fontSize = MaterialTheme.typography.h6.fontSize) }

            )
        }
    }
}

@Composable
fun AddNewProduct(cardViewModel: CardViewModel, showDialog : MutableState<Boolean>){

    val openDialog = remember { mutableStateOf(true) }
    var name : String by remember { mutableStateOf("") }
    var amount : String by remember { mutableStateOf("") }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Добавить продукт")
            },
            text = {
                Column() {
                    TextField(
                        value = name,
                        onValueChange = { name = it }
                    )
                    TextField(
                        value = amount,
                        onValueChange = { amount = it }
                    )
                }
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                ) {
                    Button(
                        onClick = {
                            val product = CardModel(0, name, amount)
                            cardViewModel.addProduct(product)
                            openDialog.value = false
                            showDialog.value = false
                        }
                    ) {
                        Text("Добавить")
                    }
                    Spacer(modifier = Modifier.weight(1f, true))
                    Button(
                        onClick = {
                            openDialog.value = false
                            showDialog.value = false
                        }
                    ) {
                        Text("Отмена")
                    }
                }
            }
        )
    }
}



