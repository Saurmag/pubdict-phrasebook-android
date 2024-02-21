package com.example.publicdictionary.mockdata

import com.example.publicdictionary.ui.model.Phrasebook
import com.example.publicdictionary.ui.model.Phrase
import com.example.publicdictionary.ui.model.Topic

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
            phrases = japaneseBasicTopicPhrasesList,
            countPhrases = 100
        ),
        Topic(
            title = "Numbers",
            phrases = japaneseNumberTopicPhrasesList,
            countPhrases = 100
        ),
        Topic(
            title = "Questions",
            japaneseQuestionsTopicPhrasesList,
            countPhrases = 100
        )
    )

    val japanesePhrasebook = Phrasebook(japaneseTopics)
}