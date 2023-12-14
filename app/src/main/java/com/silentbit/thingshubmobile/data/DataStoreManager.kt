package com.silentbit.thingshubmobile.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.initialize
import com.silentbit.thingshubmobile.data.firebase.FirebaseCredentialsProfile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.datastore by preferencesDataStore(name = "PREFERENCES_APP")

class DataStoreManager @Inject constructor(
    @ApplicationContext val context: Context
){
    private val datastore = context.datastore
    suspend fun loadFirebaseApp() = datastore.data.map { prefer ->

        if (FirebaseApp.getApps(context).isEmpty()){
            val options = FirebaseOptions.Builder()
                .setApiKey(prefer[stringPreferencesKey("apiKey")] ?: "")
                .setApplicationId(prefer[stringPreferencesKey("appId")] ?: "")
                .setDatabaseUrl(prefer[stringPreferencesKey("databaseUrl")] ?: "")

            Firebase.initialize(context, options.build(), "Primary")
        }else{
            FirebaseApp.getInstance("Primary")
        }

    }.first()

    suspend fun loadFirebaseCredentials() = datastore.data.map { prefer ->
        FirebaseCredentialsProfile(
            apiKey = prefer[stringPreferencesKey("apiKey")] ?: "",
            appId = prefer[stringPreferencesKey("appId")] ?: "",
            databaseUrl = prefer[stringPreferencesKey("databaseUrl")] ?: "",
            mail = prefer[stringPreferencesKey("mail")] ?: ""
        )
    }.first()

    suspend fun saveFirebaseCredentials(apiKey:String, appId:String, databaseUrl:String, mail:String){

        datastore.edit {
            it[stringPreferencesKey("apiKey")] = apiKey
            it[stringPreferencesKey("appId")] = appId
            it[stringPreferencesKey("databaseUrl")] = databaseUrl
            it[stringPreferencesKey("mail")] = mail
        }
    }


    suspend fun loadTypeServer() = datastore.data.map { prefer ->
        prefer[stringPreferencesKey("nameServer")] ?: ""
    }.first()
    suspend fun saveTypeServer(nameServer: String) {
        datastore.edit {
            it[stringPreferencesKey("nameServer")] = nameServer
        }
    }

}