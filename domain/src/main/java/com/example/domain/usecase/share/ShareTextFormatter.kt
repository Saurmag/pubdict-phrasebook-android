package com.example.domain.usecase.share

interface ShareTextFormatter <in T> {
    fun format(formatted: T): String
}