package org.christophertwo.xlotl.session.impl.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.christophertwo.xlotl.session.api.SessionRepository

class DataStoreSessionRepository(
    private val dataStore: DataStore<Preferences>
) : SessionRepository {

    private companion object {
        val USER_ID_KEY = stringPreferencesKey("user_id")
    }

    override fun isUserLoggedIn(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[USER_ID_KEY] != null
        }
    }

    override suspend fun saveUserSession(uid: String): Result<Unit> {
        return try {
            dataStore.edit { preferences ->
                preferences[USER_ID_KEY] = uid
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun clearUserSession(): Result<Unit> {
        return try {
            dataStore.edit { preferences ->
                preferences.remove(USER_ID_KEY)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCurrentUserId(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[USER_ID_KEY]
        }
    }
}