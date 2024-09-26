package com.example.data_repository.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class RepositoryConfiguration @Inject constructor(
    val scope: CoroutineScope,
    val dispatcher: CoroutineDispatcher
)
