package com.example.data_local.datasource

import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.example.data_local.TranslationLanguagePreferences
import com.example.domain.entity.UseCaseException
import java.io.InputStream
import java.io.OutputStream

object TranslationLanguagePreferencesSerializer : Serializer<TranslationLanguagePreferences> {
    private const val DEFAULT_ID = 0
    private const val DEFAULT_TITLE = "Русский"
    private const val DEFAULT_ISO = "rus"
    override val defaultValue: TranslationLanguagePreferences =
        TranslationLanguagePreferences
            .getDefaultInstance()
            .toBuilder()
            .setId(DEFAULT_ID)
            .setTitle(DEFAULT_TITLE)
            .setIso(DEFAULT_ISO)
            .build()

    override suspend fun readFrom(input: InputStream): TranslationLanguagePreferences {
        try {
            return TranslationLanguagePreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw UseCaseException.LanguageException(exception)
        }
    }

    override suspend fun writeTo(t: TranslationLanguagePreferences, output: OutputStream) {
        return t.writeTo(output)
    }
}