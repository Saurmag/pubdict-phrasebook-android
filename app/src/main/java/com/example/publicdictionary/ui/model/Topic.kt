package com.example.publicdictionary.ui.model


data class Topic(
    val id: Int,
    val title: String,
    val phrases: List<Phrase>,
    val countPhrases: Int
)
