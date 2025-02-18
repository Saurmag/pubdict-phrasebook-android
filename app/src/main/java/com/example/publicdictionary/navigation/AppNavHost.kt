package com.example.publicdictionary.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.presentation_dictionary.dictionary.DictionaryBaseRoute
import com.example.presentation_dictionary.dictionary.dictionaryComponent
import com.example.presentation_dictionary.phrase.navigateToPhrase
import com.example.presentation_dictionary.topic.navigateToTopic
import com.example.presentation_dictionary.word.single.navigateToWord

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        startDestination = DictionaryBaseRoute,
        navController = navController
    ) {
        dictionaryComponent(
            onBackClick = navController::popBackStack,
            onPhraseClick = navController::navigateToPhrase,
            onTopicClick = navController::navigateToTopic,
            onWordClick = navController::navigateToWord
        )
    }
}

