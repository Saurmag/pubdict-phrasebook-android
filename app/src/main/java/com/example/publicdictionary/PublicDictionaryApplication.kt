package com.example.publicdictionary

import android.app.Application
import com.example.publicdictionary.data.AppContainer
import com.example.publicdictionary.data.DefaultAppContainer

class PublicDictionaryApplication : Application() {
    private var _container: AppContainer? = null
    val container
        get() = checkNotNull(_container)
    override fun onCreate() {
        super.onCreate()
        _container = DefaultAppContainer()
    }
}