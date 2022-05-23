package com.example.mycard.Project.MVVM.View.Screens

sealed class Screens(val route : String){
    object Home : Screens(route = "home_route")
    object Settings : Screens(route ="settings_route")
}
