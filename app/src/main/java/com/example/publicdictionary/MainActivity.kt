package com.example.publicdictionary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.publicdictionary.ui.screens.PublicDictionaryApp
import com.example.publicdictionary.ui.theme.PublicDictionaryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PublicDictionaryTheme{
                val navController = rememberNavController()
                PublicDictionaryApp(navController)
            }
        }
    }
}

