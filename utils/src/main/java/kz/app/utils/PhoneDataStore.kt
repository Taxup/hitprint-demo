package kz.app.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PhoneDataStore(
    private val context: Context
) {

    // to make sure there's only one instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userPhone")
        val USER_PHONE_KEY = stringPreferencesKey("user_phone")
        val USER_SESSION_KEY = stringPreferencesKey("user_session")
    }

    //get the saved phone
    val getPhone: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[USER_PHONE_KEY] ?: ""
        }

    //save phone into datastore
    suspend fun savePhone(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_PHONE_KEY] = name
        }
    }

    //save session into datastore
    suspend fun saveSession(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_SESSION_KEY] = name
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { it.clear() }
    }

    fun hasSession() = context.dataStore.data.map { it[USER_SESSION_KEY] != null }

}