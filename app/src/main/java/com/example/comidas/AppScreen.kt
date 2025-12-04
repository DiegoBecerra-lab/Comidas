package com.example.comidas

sealed class AppScreen(val route: String) {
    object Auth : AppScreen("auth")
    object Login : AppScreen("login")
    object Register : AppScreen("register")
    object Main : AppScreen("main")
}