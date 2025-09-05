package com.bryson.gweather.presentation.screen

sealed class Screen(val route: String) {
    object Auth : Screen("auth")
    object Main : Screen("main")
}
