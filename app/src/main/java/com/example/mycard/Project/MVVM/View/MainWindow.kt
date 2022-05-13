package com.example.mycard.Project.MVVM.View

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

@ExperimentalMaterialApi
@Composable
fun MainWindow(cardViewModel: CardViewModel){

    val getAllProductsVM = cardViewModel.getAllProducts.collectAsState(initial = listOf()).value

    Scaffold(topBar = {TopAppBarCard(cardViewModel = cardViewModel)}) {
        Box() {
            CustomLazyColumnItem(list = getAllProductsVM)
        }
    }
}

@Composable
fun TopAppBarCard(cardViewModel: CardViewModel){

    TopAppBar(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Продукты", fontWeight = FontWeight.Bold, fontStyle = MaterialTheme.typography.h1.fontStyle)
        Spacer(Modifier.weight(1f, true))
        Button(onClick = {
            AddNewProduct(cardViewModel = cardViewModel)
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "AddProduct")
        }
    }



}

@ExperimentalMaterialApi
@Composable
fun CustomLazyColumnItem(list : List<CardModel>) {
    LazyColumn() {
        items(list) { product ->
            ListItem(
                text = { Text(text = product.productName) },
                trailing = { Text(text = product.productAmount) }

            )
        }
    }
}



fun AddNewProduct(cardViewModel: CardViewModel){
    val test = CardModel(1, "Banana", "10")
    cardViewModel.addProduct(test)

    /*

    AlertDialog(
        onDismissRequest = { },
        title = {
            Text(text = "Добавить предмет")
        },
        confirmButton = {
            Button(onClick = {
                cardViewModel.addProduct(test)
            }){
                Text(text = "Подтвердить")
            }
        }
    )
     */
}



