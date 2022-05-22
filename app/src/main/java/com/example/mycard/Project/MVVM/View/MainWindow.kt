package com.example.mycard.Project.MVVM.View

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mycard.MainActivity
import com.example.mycard.Project.MVVM.Models.CardModel
import com.example.mycard.Project.MVVM.ViewModels.CardViewModel
import com.example.mycard.Project.Room.Repository.CardRepository
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

@DelicateCoroutinesApi
@SuppressLint("UnrememberedMutableState")

@ExperimentalMaterialApi
@Composable
fun MainWindow(cardViewModel: CardViewModel, obj : MainActivity){
    val getAllProductsVM = cardViewModel.getAllProducts.collectAsState(initial = listOf()).value
    val showDialog = mutableStateOf(false)
    val deleteDialog = mutableStateOf(false)
    val openDialogAddNewProduct = remember { mutableStateOf(false) }

    Scaffold(topBar = {TopAppBarCard(cardViewModel = cardViewModel, showDialog = showDialog, deleteDialog = deleteDialog, obj = obj, productList = getAllProductsVM, openDialogAddNewProduct = openDialogAddNewProduct)}) {
        CustomLazyColumnItem(list = getAllProductsVM)
    }
}

@DelicateCoroutinesApi
@Composable
fun TopAppBarCard(cardViewModel: CardViewModel, showDialog : MutableState<Boolean>, deleteDialog: MutableState<Boolean>, obj: MainActivity, productList : List<CardModel>, openDialogAddNewProduct : MutableState<Boolean>){
    TopAppBar(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Products", fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.h5.fontSize)
        Spacer(Modifier.weight(1f, true))
        Button(onClick = {
            openDialogAddNewProduct.value = true
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
            AddNewProduct(cardViewModel = cardViewModel, showDialog = showDialog, obj = obj, openDialogAddNewProduct = openDialogAddNewProduct)
        }
        if(deleteDialog.value) {
            if(productList.isNotEmpty()){
                DeleteAllDialog(deleteDialog = deleteDialog, cardViewModel = cardViewModel)
            }
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
                trailing = { Text(text = "Amount: " + product.productAmount, fontSize = MaterialTheme.typography.h6.fontSize) }
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
            Text(text = "Delete all?")
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
                    Text("Delete")
                }
                Spacer(modifier = Modifier.weight(1f, true))
                Button(
                    onClick = {
                        openDialog.value = false
                        deleteDialog.value = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        }
    )
}



@Composable
fun AddNewProduct(cardViewModel: CardViewModel, showDialog : MutableState<Boolean>, obj: MainActivity, openDialogAddNewProduct : MutableState<Boolean>){

    var name : String by remember { mutableStateOf("") }
    var amount : String by remember { mutableStateOf("") }
    var expanded : Boolean by remember { mutableStateOf(false)}
    var dropDownMenuItems = listOf<String>()
    val api = "7e843a8220f14d5ba2891e686e661e9a"

    cardViewModel.getTextApi(api, name, "5")

    cardViewModel.myResponse.observe(obj, Observer { response ->
        val result = response.body()?.size
        if(response.isSuccessful) {
            if (response.body().isNullOrEmpty()) {}
            else{
                if (result != null) {
                    dropDownMenuItems = response.body()?.subList(0,result)?.map {
                        it.name
                    }!!
                }
            }
        }else{
            when(response.code()){
                500 -> Toast.makeText(obj, "Server error", Toast.LENGTH_SHORT).show()
                423 -> Toast.makeText(obj, "Server blocked", Toast.LENGTH_SHORT).show()
                410 -> Toast.makeText(obj, "The server isn't exist", Toast.LENGTH_SHORT).show()
                400 -> Toast.makeText(obj, "Can't validate call", Toast.LENGTH_SHORT).show()
                401 -> Toast.makeText(obj, "Non authorized", Toast.LENGTH_SHORT).show()
            }
        }
    })
    if (openDialogAddNewProduct.value) {
        AlertDialog(
            onDismissRequest = {
                openDialogAddNewProduct.value = false
            },
            title = {
                Text(text = "Add product")
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            expanded = true
                            if(it.length <= 50) {
                                name = it
                            }
                                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        label = {Text("Name")},
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { },
                        properties = PopupProperties(focusable = false),
                        offset = DpOffset(0.dp, (-60).dp)
                    ) {
                        dropDownMenuItems.forEach { label ->
                            DropdownMenuItem(onClick = {
                                name = label
                                expanded = false
                            }) {
                                Text(text = label)
                            }
                        }
                    }
                    OutlinedTextField(
                        value = amount,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = {
                            if(it.length <= 3) {
                                amount = it
                            }
                                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        label = {Text("Amount")}
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
                            if (name.isNotEmpty() && amount.isNotEmpty()){
                                cardViewModel.addProduct(product)
                                openDialogAddNewProduct.value = false
                                showDialog.value = false
                            }else{
                                Toast.makeText(obj, "Fields text is incorrect!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    ) {
                        Text("Add")
                    }
                    Spacer(modifier = Modifier.weight(1f, true))
                    Button(
                        onClick = {
                            openDialogAddNewProduct.value = false
                            showDialog.value = false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            }
        )
    }
}


