package com.example.jetpackcompose.util

import androidx.datastore.core.DataStore
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "DateStore")
private val LAST_DATE_KEY = stringPreferencesKey("last_date")

fun getStartOfWeek(date: LocalDate): LocalDate {
    return date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
}

fun isSameDayAsLastDate(context: Context): Boolean {
    // Use runBlocking to make the function synchronous
    return runBlocking {
        val lastDate = context.dataStore.data.map { preferences ->
            preferences[LAST_DATE_KEY]
        }.first()

        val today = LocalDate.now().toString()
        today == lastDate
    }
}

suspend fun saveCurrentDate(context: Context) {
    context.dataStore.edit { preferences ->
        preferences[LAST_DATE_KEY] = LocalDate.now().toString()
    }
}




