package com.example.presentation_common.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument

private const val ROUTE_WORDS_OF_DAY = "words_of_day/"

private const val ROUTE_PHRASEBOOK ="phrasebook/topics"

private const val ROUTE_TOPIC = "phrasebook/topics/%s"
private const val ARG_TOPIC_ID = "topicId"

private const val ROUTE_PHRASE = "phrase/%s"
private const val ARG_PHRASE_ID = "phraseId"

sealed class NavRoutes(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {

    object WordsOfDay : NavRoutes(ROUTE_WORDS_OF_DAY)

    object Phrasebook : NavRoutes(ROUTE_PHRASEBOOK)

    object Topic : NavRoutes(
        route = String.format(
            format = ROUTE_TOPIC, "{$ARG_TOPIC_ID}"
        ),
        arguments = listOf(navArgument(ARG_TOPIC_ID){
            type = NavType.IntType
        })
    ) {
        fun routeForTopic(topicInput: TopicInput) =
            String.format(ROUTE_TOPIC, topicInput.id)

        fun fromEntry(entry: NavBackStackEntry): TopicInput =
            TopicInput(
                id = entry.arguments?.getInt(ARG_TOPIC_ID) ?: 0
            )
    }

    object Phrase : NavRoutes(
        route = String.format(
            ROUTE_PHRASE, "{$ARG_PHRASE_ID}"
        ),
        arguments = listOf(navArgument(ARG_PHRASE_ID) {
            type = NavType.IntType
        })
    ) {
        fun routeForPhrase(phraseInput: PhraseInput) =
            String.format(ROUTE_PHRASE, phraseInput.id)

        fun fromEntry(entry: NavBackStackEntry): PhraseInput =
            PhraseInput(
                id = entry.arguments?.getInt(ARG_PHRASE_ID) ?: 0
            )
    }
}