package com.silentbit.thingshubmobile.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.datastore by preferencesDataStore(name = "PREFERENCES_APP")

class DataStoreManager @Inject constructor(
    @ApplicationContext val context: Context
){
    private val datastore = context.datastore

    suspend fun loadFirebaseCredentials() = datastore.data.map { prefer ->
        FirebaseCredentialsProfile(
            address = prefer[stringPreferencesKey("address")] ?: "",
            token = prefer[stringPreferencesKey("token")] ?: ""
        )
    }

    suspend fun saveFirebaseCredentials(database:String, token:String){
        datastore.edit {
            it[stringPreferencesKey("address")] = database
            it[stringPreferencesKey("token")] = token
        }
    }

}