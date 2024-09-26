package com.example.data_remote.network.model.phrase

import com.squareup.moshi.FromJson

class NetworkPhraseJsonAdapterClass {

    @FromJson
    fun fromJson(networkJsonPhrase: NetworkPhraseJson): NetworkPhrase {
        var translationOrigin: String = ""
        var translation: String = ""
        var phoneticIpa: String? = ""
        var phoneticEn:String? = ""

        if (networkJsonPhrase.translationList.size == 2) {
            if (networkJsonPhrase.translationList.first().id % 2 == 1) {
                networkJsonPhrase.translationList.first().let {
                    translationOrigin = it.translation
                    phoneticIpa = it.ipaPhonetic
                    phoneticEn = it.enPhonetic
                }
                translation = networkJsonPhrase.translationList.last().translation
            } else {
                networkJsonPhrase.translationList.last().let {
                    translationOrigin = it.translation
                    phoneticIpa = it.ipaPhonetic
                    phoneticEn = it.enPhonetic
                }
                translation = networkJsonPhrase.translationList.first().translation
            }
        }

        return NetworkPhrase(
            id = networkJsonPhrase.id,
            categoryId = networkJsonPhrase.categoryId,
            translationOrigin = translationOrigin,
            translation = translation,
            phoneticIpa = phoneticIpa,
            phoneticEn = phoneticEn
        )
    }
}