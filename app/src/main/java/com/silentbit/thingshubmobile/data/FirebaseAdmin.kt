package com.silentbit.thingshubmobile.data

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.initialize
import javax.inject.Inject

class FirebaseAdmin @Inject constructor() {

    fun getFirebaseApp(context:Context, apiKey:String, idApp:String, dataBaseUrl:String): FirebaseApp {

        val options = FirebaseOptions.Builder()
            .setApiKey(apiKey)
            .setApplicationId(idApp)
            .setDatabaseUrl(dataBaseUrl)
            .build()

        return Firebase.initialize(context, options)

    }

}