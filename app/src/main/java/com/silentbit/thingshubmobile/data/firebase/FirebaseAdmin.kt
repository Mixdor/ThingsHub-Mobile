package com.silentbit.thingshubmobile.data.firebase

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.initialize
import javax.inject.Inject

class FirebaseAdmin @Inject constructor() {

    fun getFirebaseApp(context:Context, apiKey:String, idApp:String, dataBaseUrl:String): FirebaseApp {

        val listApps = FirebaseApp.getApps(context)
        if (listApps.isNotEmpty()){
            for (app in listApps){
                app.delete()
            }
        }

        val options = FirebaseOptions.Builder()
            .setApiKey(apiKey)
            .setApplicationId(idApp)
            .setDatabaseUrl(dataBaseUrl)

        return Firebase.initialize(context, options.build(),"Primary")

    }

}