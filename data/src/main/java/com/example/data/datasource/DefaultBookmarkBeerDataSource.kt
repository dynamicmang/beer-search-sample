package com.example.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class DefaultBookmarkBeerDataSource @Inject constructor(
    @Named("bookmark") private val dataStore: DataStore<Preferences>,
) : BookmarkBeerDataSource {

    object PreferencesKey {
        val BOOKMARKED_BEER = stringSetPreferencesKey("BOOKMARKED_BEER")
    }

    override suspend fun getBookmarkBeer(): Flow<Set<String>> {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKey.BOOKMARKED_BEER] ?: emptySet()
        }
    }

    override suspend fun updateBookmarkBeer(bookmarkBeer: Set<String>) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.BOOKMARKED_BEER] = bookmarkBeer
        }
    }

}