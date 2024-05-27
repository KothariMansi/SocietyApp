package com.example.societyapp.ui.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore by preferencesDataStore(name = "worker_category_data_store")

suspend fun saveWorkerCategoryList(context: Context, list: List<String>) {
    context.dataStore.edit { preferences ->
        preferences[PreferencesKeys.WORKER_CATEGORY] = list.toSet()
    }
}
fun getWorkerCategory(context: Context): Flow<List<String>> {
    return context.dataStore.data.map {preferences ->
        preferences[PreferencesKeys.WORKER_CATEGORY]?.toList() ?: emptyList()
    }
}


object PreferencesKeys {
    val WORKER_CATEGORY = stringSetPreferencesKey("worker_category")
}