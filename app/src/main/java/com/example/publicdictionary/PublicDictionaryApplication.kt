package com.example.publicdictionary

import android.app.Application
import com.example.publicdictionary.data.AppContainer
import com.example.publicdictionary.data.DefaultAppContainer

class PublicDictionaryApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}