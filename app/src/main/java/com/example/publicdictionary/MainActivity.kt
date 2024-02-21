package com.example.publicdictionary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.publicdictionary.ui.screens.PublicDictionaryApp
import com.example.publicdictionary.ui.theme.PublicDictionaryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PublicDictionaryTheme{
                // A surface container using the 'background' color from the theme
                PublicDictionaryApp()
            }
        }
    }
}

