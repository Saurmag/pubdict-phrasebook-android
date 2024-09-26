package com.example.data_local

import com.example.data_local.datasource.LocalDataSourceConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestScope

class LocalDataSourceConfig(
    val dispatcher: LocalDataSourceConfiguration = LocalDataSourceConfiguration(Dispatchers),
    val scope: LocalDataSourceCoroutineScope = LocalDataSourceCoroutineScope(TestScope())
)