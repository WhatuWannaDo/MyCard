package com.example.mycard.Project.MVVM.View

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.ViewModelProvider
import com.example.mycard.Project.MVVM.Models.CardModel
import com.example.mycard.Project.MVVM.ViewModels.CardViewModel
import com.example.mycard.Project.Room.Repository.CardRepository
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

@DelicateCoroutinesApi
@SuppressLint("UnrememberedMutableState")

@ExperimentalMaterialApi
@Composable
fun MainWindow(cardViewModel: CardViewModel){
    val getAllProductsVM = cardViewModel.getAllProducts.collectAsState(initial = listOf()).value
    val showDialog = mutableStateOf(false)
    val deleteDialog = mutableStateOf(false)

    Scaffold(topBar = {TopAppBarCard(cardViewModel = cardViewModel, showDialog = showDialog, deleteDialog = deleteDialog)}) {
        CustomLazyColumnItem(list = getAllProductsVM)
    }
}

@DelicateCoroutinesApi
@Composable
fun TopAppBarCard(cardViewModel: CardViewModel, showDialog : MutableState<Boolean>, deleteDialog: MutableState<Boolean>){
    TopAppBar(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Продукты", fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.h5.fontSize)
        Spacer(Modifier.weight(1f, true))
        Button(onClick = {
            showDialog.value = true
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "AddProduct", modifier = Modifier.size(26.dp))
        }
        Button(onClick = {
            deleteDialog.value = true
        }) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "DeleteAll", modifier = Modifier.size(26.dp))
        }
        if(showDialog.value) {
            AddNewProduct(cardViewModel = cardViewModel, showDialog = showDialog)
        }
        if(deleteDialog.value) {
            DeleteAllDialog(deleteDialog = deleteDialog, cardViewModel = cardViewModel)
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
@DelicateCoroutinesApi
@Composable
fun DeleteAllDialog(deleteDialog : MutableState<Boolean>, cardViewModel: CardViewModel){
    val openDialog = remember { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = {
            deleteDialog.value = false
        },
        title = {
            Text(text = "Удалить всё?")
        },
        buttons = {
            Row(
                modifier = Modifier.padding(vertical = 20.dp, horizontal = 4.dp),
            ) {
                Button(
                    onClick = {
                        GlobalScope.launch(Dispatchers.IO) {
                            cardViewModel.deleteAllProducts()
                        }
                    }
                ) {
                    Text("Удалить")
                }
                Spacer(modifier = Modifier.weight(1f, true))
                Button(
                    onClick = {
                        openDialog.value = false
                        deleteDialog.value = false
                    }
                ) {
                    Text("Отмена")
                }
            }
        }
    )
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
                Column {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        modifier = Modifier
                            .fillMaxWidth(),
                        label = {Text("Название")},
                    )
                    OutlinedTextField(
                        value = amount,
                        onValueChange = { amount = it },
                        modifier = Modifier
                            .fillMaxWidth(),
                        label = {Text("Количество")}
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
                            if (name.isNotEmpty() || amount.isNotEmpty()){
                                cardViewModel.addProduct(product)
                                openDialog.value = false
                                showDialog.value = false
                            }
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



