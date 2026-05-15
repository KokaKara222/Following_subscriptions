package com.example.following_subscriptions

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.following_subscriptions.ui.theme.DeepBlue
import com.example.following_subscriptions.ui.theme.Following_subscriptionsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Following_subscriptionsTheme {
                var currentScreen by remember { mutableStateOf("login") }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = DeepBlue
                ) {
                    when (currentScreen) {
                        "login" -> {
                            LoginScreen(
                                onLoginClick = { currentScreen = "main"},
                                onRegister = {currentScreen = "register"})
                        }
                        "register" ->{
                            RegistrationScreen(
                                onRegistrationClick = {currentScreen = "main"},
                                onBackToLogin = {currentScreen = "login"})
                        }
                        "main", "setting", "stats" -> {
                            MainListScreen(
                                startScreen = currentScreen,
                                onNavigate ={ newScreen -> currentScreen = newScreen}
                            )
                        }
                    }
                }
            }
        }
    }
}

