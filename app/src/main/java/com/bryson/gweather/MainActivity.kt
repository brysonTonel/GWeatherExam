package com.bryson.gweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bryson.gweather.presentation.MainScreen
import com.bryson.gweather.presentation.screen.AuthScreen
import com.bryson.gweather.presentation.screen.Screen
import com.bryson.gweather.ui.theme.GWeatherTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GWeatherTheme {

                val navController = rememberNavController()

                Surface(color = MaterialTheme.colorScheme.background) {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Auth.route
                    ) {
                        composable(Screen.Auth.route) {
                            AuthScreen(onSuccess = {
                                navController.navigate(Screen.Main.route) {
                                    popUpTo(Screen.Auth.route) { inclusive = true }
                                }
                            })
                        }
                        composable(Screen.Main.route) {
                            MainScreen(
                                onLogout = {
                                    FirebaseAuth.getInstance().signOut()
                                    navController.navigate(Screen.Auth.route) {
                                        popUpTo(Screen.Auth.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
