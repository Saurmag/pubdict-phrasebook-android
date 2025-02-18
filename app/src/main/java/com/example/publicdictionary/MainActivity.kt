package com.example.publicdictionary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.data_local.injection.DaggerDataLocalComponent
import com.example.data_local.injection.DataLocalComponent
import com.example.data_remote.injection.DaggerDataRemoteComponent
import com.example.data_remote.injection.DataRemoteComponent
import com.example.data_repository.injection.DaggerDataRepositoryComponent
import com.example.data_repository.injection.DataRepositoryComponent
import com.example.presentation_common.design.PubDictTheme
import com.example.presentation_dictionary.injection.DictionaryDepsStore
import com.example.publicdictionary.injection.AppComponent
import com.example.publicdictionary.injection.DaggerAppComponent
import com.example.publicdictionary.injection.DaggerHelperComponent
import com.example.publicdictionary.injection.HelperComponent
import com.example.publicdictionary.navigation.AppNavHost

class MainActivity : ComponentActivity() {

    private val dataLocalComponent: DataLocalComponent by lazy {
        DaggerDataLocalComponent
            .builder()
            .context(applicationContext)
            .build()
    }

    private val dataRemoteComponent: DataRemoteComponent by lazy {
        DaggerDataRemoteComponent.create()
    }

    private val dataRepositoryComponent: DataRepositoryComponent by lazy {
        DaggerDataRepositoryComponent.builder()
            .depsRemotePhrasebookDataSource(dataRemoteComponent)
            .depsLocalPhrasebookDataSource(dataLocalComponent)
            .build()
    }

    private val helperComponent: HelperComponent by lazy {
        DaggerHelperComponent
            .builder()
            .applicationContext(application)
            .activityContext(this)
            .build()
    }

    private var _appComponent: AppComponent? = null

    val appComponent: AppComponent
        get() = checkNotNull(_appComponent)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _appComponent = DaggerAppComponent.builder()
            .depsHelperComponent(helperComponent)
            .depsDataLocalComponent(dataLocalComponent)
            .depsDataRemoteComponent(dataRemoteComponent)
            .depsDataRepositoryComponent(dataRepositoryComponent)
            .build()

        DictionaryDepsStore.apply {
            repositoryDeps = appComponent
            contextDeps = appComponent
            activityDeps = appComponent
        }

        enableEdgeToEdge()
        setContent {
            PubDictTheme {
                val navController = rememberNavController()
                AppNavHost(
                    navController = navController
                )
            }
        }
    }
}