package com.example.mycard.Project.MVVM.View

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.example.mycard.Project.ui.MainActivity
import com.example.mycard.Project.data.models.databaseModels.CardModel
import com.example.mycard.Project.data.models.databaseModels.FolderModel
import com.example.mycard.Project.ui.viewModels.CardViewModel
import com.example.mycard.Project.data.network.API_KEY
import com.example.mycard.Project.ui.screens.Screens
import com.example.mycard.Project.ui.viewModels.FolderViewModel
import kotlinx.coroutines.*
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@DelicateCoroutinesApi
@SuppressLint("UnrememberedMutableState")

@ExperimentalMaterialApi
@Composable
fun MainWindow(cardViewModel: CardViewModel, obj : MainActivity, navController: NavController, folderViewModel: FolderViewModel){
    val getAllProductsVM = cardViewModel.getAllProducts.collectAsState(initial = listOf()).value
    val getAllFoldersVM = folderViewModel.getAllFolders.collectAsState(initial = listOf()).value

    val showDialog = mutableStateOf(false)
    val deleteDialog = mutableStateOf(false)
    val openDialogAddNewProduct = remember { mutableStateOf(false) }
    val alertDialogDescription = remember { mutableStateOf(false) }
    val api = API_KEY
    val noDataState = mutableStateOf(true)


    val sharedPrefs : SharedPreferences = obj.getSharedPreferences("descr", MODE_PRIVATE)

    Scaffold(topBar = {
        TopAppBarCard(cardViewModel = cardViewModel,
            showDialog = showDialog,
            deleteDialog = deleteDialog,
            obj = obj,
            productList = getAllProductsVM,
            folderList = getAllFoldersVM,
            openDialogAddNewProduct = openDialogAddNewProduct,
            api = api,
            navController = navController)
        AlertDialogDescription(alertDialogDescription = alertDialogDescription, sharedPreferences = sharedPrefs)}
    ){
        CustomLazyColumnItem(list = getAllProductsVM, getAllFoldersVM, alertDialogDescription = alertDialogDescription, sharedPreferences = sharedPrefs, viewModel = cardViewModel, noDataState)
        NoDataText(noDataState = noDataState)

    }
}

@DelicateCoroutinesApi
@Composable
fun TopAppBarCard(
    cardViewModel: CardViewModel,
    showDialog : MutableState<Boolean>,
    deleteDialog: MutableState<Boolean>,
    obj: MainActivity,
    productList : List<CardModel>,
    folderList: List<FolderModel>,
    openDialogAddNewProduct : MutableState<Boolean>,
    api : String,
    navController: NavController
){
    val buttonMode : Color
    val iconMode = Color.White
    if (isSystemInDarkTheme()){
        buttonMode = Color(39,39,39)
    }else{
        buttonMode = Color(98,0,238)
    }
    TopAppBar(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Ingredients", fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.h5.fontSize)
        Spacer(Modifier.weight(1f, true))
        Button(onClick = {
            openDialogAddNewProduct.value = true
            showDialog.value = true
        }, colors = ButtonDefaults.buttonColors(backgroundColor = buttonMode)) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "AddProduct", modifier = Modifier.size(26.dp), tint = iconMode)
        }
        Button(onClick = {
            deleteDialog.value = true
        }, colors = ButtonDefaults.buttonColors(backgroundColor = buttonMode)) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "DeleteAll", modifier = Modifier.size(26.dp), tint = iconMode)
        }
        Button(onClick = {
            navController.navigate(route = Screens.Settings.route)
        }, colors = ButtonDefaults.buttonColors(backgroundColor = buttonMode)) {
            Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings", modifier = Modifier.size(26.dp), tint = iconMode)
        }
        if(showDialog.value) {
            AddNewProduct(cardViewModel = cardViewModel, showDialog = showDialog, obj = obj, openDialogAddNewProduct = openDialogAddNewProduct, api = api)
        }
        if(deleteDialog.value) {
            if((productList.isNotEmpty()) && (folderList.isNotEmpty())){
                DeleteAllDialog(deleteDialog = deleteDialog, cardViewModel = cardViewModel)
            }else{
                Toast.makeText(obj, "No data here", Toast.LENGTH_SHORT).show()
            }
        }

    }

}

@SuppressLint("CommitPrefEdits")
@ExperimentalMaterialApi
@Composable
fun CustomLazyColumnItem(
    list : List<CardModel>,
    folderList : List<FolderModel>,
    alertDialogDescription : MutableState<Boolean>,
    sharedPreferences: SharedPreferences,
    viewModel: CardViewModel,
    noDataState : MutableState<Boolean>
) {
    val editor = sharedPreferences.edit()
    val backgroundMode : Color
    if (isSystemInDarkTheme()){
        backgroundMode = Color(20,20,20)
    }else{
        backgroundMode = Color.White
    }
    LazyColumn(contentPadding = PaddingValues(vertical = 10.dp, horizontal = 5.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {

        if((list.isNotEmpty()) || (folderList.isNotEmpty())){
            noDataState.value = false
        }

        items(list) { product ->
            //swipe to delete

            val archive = SwipeAction(
                onSwipe = {
                    viewModel.deleteProduct(CardModel(product.id, product.productName, product.productAmount, product.description))
                },
                icon = {Icon(imageVector = Icons.Default.Delete, contentDescription = "DeleteOnSwipe", modifier = Modifier.padding(16.dp), tint = Color.White)},
                background = Color.Red,
            )
            SwipeableActionsBox(endActions = listOf(archive), backgroundUntilSwipeThreshold = Color.Green, swipeThreshold = 200.dp) {
                ListItem(
                    modifier = Modifier
                        .border(2.dp, color = Color.Green, shape = RoundedCornerShape(15.dp))
                        .background(backgroundMode)
                        .clickable {
                            alertDialogDescription.value = true
                            editor
                                .putString("descriptionItem", product.description.toString())
                                .apply()
                        },
                    text = { Text(text = product.productName, fontSize = MaterialTheme.typography.h6.fontSize) },
                    trailing = { Text(text = "Amount: " + product.productAmount, fontSize = MaterialTheme.typography.h6.fontSize) },
                )
            }
        }

        items(folderList){ folder ->
            ListItem(
                modifier = Modifier
                    .border(2.dp, color = Color.Green, shape = RoundedCornerShape(15.dp))
                    .background(backgroundMode)
                    .clickable {

                    },
                icon = ({ Icon(imageVector = Icons.Default.List, contentDescription = "folder") }),
                text = { Text(text = folder.name)}
            )
        }
    }
}
// alert dialog for description when item from lazy column selected
@SuppressLint("CommitPrefEdits")
@ExperimentalMaterialApi
@Composable
fun AlertDialogDescription(alertDialogDescription: MutableState<Boolean>, sharedPreferences: SharedPreferences){
    var text =
        if (sharedPreferences.getString("descriptionItem", "No data").toString().isNotEmpty()) {
            sharedPreferences.getString("descriptionItem", "No data").toString()
        }else{
            "No info"
        }
    if(alertDialogDescription.value){
        AlertDialog(
            onDismissRequest = { alertDialogDescription.value = false },
            title = { Text(text = "Description", fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.h5.fontSize)},
            buttons = {
                Column(
                    modifier = Modifier.padding(vertical = 20.dp, horizontal = 4.dp),
                ) {
                    Text(text = text)
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        contentPadding = PaddingValues(horizontal = 50.dp),
                        onClick = {
                            alertDialogDescription.value = false
                        }
                    ) {
                        Text("Ok")
                    }
                }
            },

        )
    }
}

@DelicateCoroutinesApi
@Composable
fun DeleteAllDialog(
    deleteDialog : MutableState<Boolean>,
    cardViewModel: CardViewModel
){
    val openDialog = remember { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = {
            deleteDialog.value = false
        },
        title = {
            Text(text = "Delete all?", fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.h5.fontSize)
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
        },
    )
}




@Composable
fun AddNewProduct(
    cardViewModel: CardViewModel,
    showDialog : MutableState<Boolean>,
    obj: MainActivity,
    openDialogAddNewProduct : MutableState<Boolean>,
    api: String
){

    var name : String by remember { mutableStateOf("") }
    var amount : String by remember { mutableStateOf("") }
    var description : String by remember { mutableStateOf("") }
    var expanded : Boolean by remember { mutableStateOf(false)}
    var dropDownMenuItems = listOf<String>()


    //add new product alert dialog
    if (openDialogAddNewProduct.value) {
        AlertDialog(
            onDismissRequest = {
                openDialogAddNewProduct.value = false
            },
            title = {
                Text(text = "Add product", fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.h5.fontSize)
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            expanded = true
                            cardViewModel.getTextApi(api, name, "5")

                            //trying to get value from api

                            cardViewModel.myResponse.observe(obj, Observer { response ->
                                val result = response.body()?.size
                                if(response.isSuccessful) {
                                    if (response.body().isNullOrEmpty()) {}
                                    else{
                                        if (result != null) {

                                            //setting dropdown menu items

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
                                        402 -> Toast.makeText(obj, "Limited", Toast.LENGTH_SHORT).show()

                                    }
                                }
                            })
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
                        onDismissRequest = { expanded = false },
                        properties = PopupProperties(focusable = false),
                        offset = DpOffset(0.dp, -120.dp)
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
                    OutlinedTextField(
                        value = description,
                        singleLine = true,
                        onValueChange = {
                            if(it.length <= 250) {
                                description = it
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        label = {Text("Description(optional)")}
                    )
                }
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                ) {

                    //add item into a data base

                    Button(
                        onClick = {
                            val product = CardModel(0, name, amount, description)
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


