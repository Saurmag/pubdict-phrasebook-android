package com.example.publicdictionary.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument

private const val ROUTE_TOPIC_LIST = "topics"
private const val ROUTE_TOPIC = "topic/%s"
private const val ARG_TOPIC_ID = "topicId"
private const val ROUTE_PHRASE = "phrase/%s"
private const val ARG_PHRASE_ID = "phraseId"

sealed class NavRoutes(
    val routes: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    object TopicList : NavRoutes(ROUTE_TOPIC_LIST)

    object Topic : NavRoutes(
        routes = String.format(ROUTE_TOPIC, "{$ARG_TOPIC_ID}"),
        arguments = listOf(navArgument(name = ARG_TOPIC_ID){
            type = NavType.IntType
        })
    ) {
        fun routeForTopic(topicInput: TopicInput) = String.format(ROUTE_TOPIC, topicInput.topicId)

        fun fromEntry(entry: NavBackStackEntry): TopicInput {
            return TopicInput(topicId = entry.arguments?.getInt(ARG_TOPIC_ID) ?: 0)
        }
    }

    object Phrase : NavRoutes(
        routes = String.format(ROUTE_PHRASE, "{$ARG_PHRASE_ID}"),
        arguments = listOf(navArgument(name = ARG_PHRASE_ID){
            type = NavType.IntType
        })
    ) {
        fun routeForPhrase(phraseInput: PhraseInput) = String.format(ROUTE_PHRASE, phraseInput.phraseId)

        fun fromEntry(entry: NavBackStackEntry): PhraseInput {
            return PhraseInput(phraseId = entry.arguments?.getInt(ARG_PHRASE_ID) ?: 0)
        }
    }
}