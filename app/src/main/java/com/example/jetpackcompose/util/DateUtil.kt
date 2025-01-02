package com.example.jetpackcompose.util

import androidx.datastore.core.DataStore
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// DataStore setup
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "DateStore")
// need one for login remember me


// Keys for DataStore
// This key will store the last updated date the Last workout is always before or equal the last updated date
private val LAST_DATE_KEY = stringPreferencesKey("last_updated_date")
private val REMEMBER_ME_KEY = stringPreferencesKey("remember_me_token")

suspend fun isFirstTimeUser(context: Context): Boolean {
    return context.dataStore.data.map { preferences ->
        preferences[LAST_DATE_KEY] == null
    }.first()
}

suspend fun getLastDate(context: Context): String {
    return context.dataStore.data.map { preferences ->
        preferences[LAST_DATE_KEY] ?: ""
    }.first()
}

// Save a new date if it is after the current last date
suspend fun saveLastDate(context: Context, date: LocalDate) {
    val lastDate = getLastDate(context)

    // Parse the last saved date, if available
    val lastDateLocal = lastDate.takeIf { it.isNotEmpty() }?.let { LocalDate.parse(it) }

    // Update the date only if it is newer
    if (lastDateLocal == null || date.isAfter(lastDateLocal)) {
        context.dataStore.edit { preferences ->
            preferences[LAST_DATE_KEY] = date.toString()
        }
    }
}

// Initialize default values for first-time users
suspend fun initializeForFirstTimeUser(context: Context) {
    val isFirstTime = isFirstTimeUser(context)
    if (isFirstTime) {
        context.dataStore.edit { preferences ->
            preferences[LAST_DATE_KEY] = LocalDate.now().minusDays(1).toString()
        }
    }
}

suspend fun resetForFirstTimeUser(context: Context) {
    context.dataStore.edit { preferences ->
        preferences[LAST_DATE_KEY] = LocalDate.now().minusDays(1).toString()
    }
}

suspend fun getRememberMeToken(context: Context): String {
    return context.dataStore.data.map { preferences ->
        preferences[REMEMBER_ME_KEY] ?: ""
    }.first()
}

suspend fun saveRememberMeToken(context: Context, token: String) {
    context.dataStore.edit { preferences ->
        preferences[REMEMBER_ME_KEY] = token
    }
}

// Utility: Check if connected to the internet
fun isInternetConnected(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

    return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

// Utility: Get the start of the week
fun getStartOfWeek(date: LocalDate): LocalDate {
    return date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
}
