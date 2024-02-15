package com.example.publicdictionary.mockdata

import com.example.publicdictionary.mockdata.mockmodel.LanguagePhrasebook
import com.example.publicdictionary.mockdata.mockmodel.Phrase
import com.example.publicdictionary.mockdata.mockmodel.Topic

object DataSource {
    private val japaneseBasicTopicPhrasesList: List<Phrase> = List(10) {
        Phrase(
        text = "hello",
        textTranslation = "こんにちは",
        ipaTextTransliteration = "konnʲitɕiha",
        enTextTransliteration = "kon ni chi ha"
        )
    }

    private val japaneseNumberTopicPhrasesList: List<Phrase> = List(10) {
        Phrase(
            text = "one",
            textTranslation = "ワン",
            ipaTextTransliteration = "wan",
            enTextTransliteration = "wan"
        )
    }

    private val japaneseQuestionsTopicPhrasesList: List<Phrase> = List(10) {
        Phrase(
            text = "when",
            textTranslation = "とき",
            ipaTextTransliteration = "tokʲi",
            enTextTransliteration = "toki"
        )
    }

    private val japaneseTopics: List<Topic> = listOf(
        Topic(
            title = "Basic",
            phrases = japaneseBasicTopicPhrasesList
        ),
        Topic(
            title = "Numbers",
            phrases = japaneseNumberTopicPhrasesList
        ),
        Topic(
            title = "Questions",
            japaneseQuestionsTopicPhrasesList
        )
    )

    val japanesePhrasebook = LanguagePhrasebook(japaneseTopics)
}