package com.example.presentation_dictionary.dictionary

import android.content.Context
import android.content.Intent
import com.example.domain.usecase.share.ShareTextFormatter
import com.example.domain.usecase.share.ShareTextUtil
import com.example.presentation_dictionary.R
import com.example.presentation_dictionary.injection.ActivityContext
import com.example.presentation_dictionary.phrase.PhraseModel
import com.example.presentation_dictionary.word.single.DetailWordModel
import javax.inject.Inject

class ShareWordFormatter @Inject constructor(
    @param:ActivityContext private val context: Context
) : ShareTextFormatter<DetailWordModel> {
    override fun format(formatted: DetailWordModel): String {
        val originText = context.getText(R.string.share_word_format_helper)
        val translatedText = context.getText(R.string.share_translated_word_format_helper)
        val ipaTranscriptionText =
            context.getText(R.string.share_ipa_transcription_word_format_helper)
        val enTranscriptionText =
            context.getText(R.string.share_en_transcription_word_format_helper)
        return String.format(
            "$originText\n%1s\n\n$translatedText\n%2s\n\n$ipaTranscriptionText\n%3s\n\n$enTranscriptionText\n%4s\n",
            formatted.text,
            formatted.translation,
            formatted.ipaTransliteration,
            formatted.enTransliteration
        )
    }
}

class SharePhraseFormatter @Inject constructor(
    @param:ActivityContext private val context: Context
) : ShareTextFormatter<PhraseModel> {
    override fun format(formatted: PhraseModel): String {
        val originText = context.getText(R.string.share_phrase_format_helper)
        val translatedText = context.getText(R.string.share_translated_phrase_format_helper)
        val ipaTranscriptionText =
            context.getText(R.string.share_ipa_transcription_phrase_format_helper)
        val enTranscriptionText =
            context.getText(R.string.share_en_transcription_phrase_format_helper)
        return String.format(
            "$originText\n%1s\n\n$translatedText\n%2s\n\n$ipaTranscriptionText\n%3s\n\n$enTranscriptionText\n%4s\n",
            formatted.originText,
            formatted.translatedText,
            formatted.ipaTextTransliteration,
            formatted.enTextTransliteration
        )
    }
}

class ShareTextUtilImpl @Inject constructor(
    private val intent: Intent,
    @param:ActivityContext private val context: Context,
): ShareTextUtil {
    override fun shareText(text: String) {
        val intentSendFromDictionary = intent.apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        val readyIntent = Intent.createChooser(intentSendFromDictionary, null)
        context.startActivity(readyIntent)
    }
}