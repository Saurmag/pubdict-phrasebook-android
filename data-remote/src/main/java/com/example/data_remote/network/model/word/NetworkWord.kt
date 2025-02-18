package com.example.data_remote.network.model.word

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkWord(
    val word: String
)
