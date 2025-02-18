package com.example.publicdictionary.injection

import android.app.Activity
import android.app.Application
import android.content.Context
import com.example.presentation_dictionary.injection.ActivityContext
import com.example.presentation_dictionary.injection.ApplicationContext
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(modules = [ActivityModule::class, ContextModule::class])
interface HelperComponent {

    @get:ApplicationContext
    val applicationContext: Context

    @get:ActivityContext
    val activityContext: Context

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun applicationContext(application: Application): Builder

        @BindsInstance
        fun activityContext(activityContext: Activity): Builder

        fun build(): HelperComponent
    }
}

@Module
class ActivityModule {

    @ActivityContext
    @Provides
    fun provideActivityContext(activityContext: Activity): Context = activityContext
}

@Module
class ContextModule {

    @ApplicationContext
    @Provides
    fun provideApplicationContext(application: Application): Context = application.applicationContext
}