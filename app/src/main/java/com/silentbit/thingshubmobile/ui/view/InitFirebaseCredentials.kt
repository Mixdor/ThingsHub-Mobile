package com.silentbit.thingshubmobile.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import com.silentbit.thingshubmobile.data.DataStoreManager
import com.silentbit.thingshubmobile.data.FirebaseAdmin
import com.silentbit.thingshubmobile.databinding.InitFirebaseCredentialsBinding
import com.silentbit.thingshubmobile.support.UiSupport
import com.silentbit.thingshubmobile.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class InitFirebaseCredentials : AppCompatActivity() {

    lateinit var binding : InitFirebaseCredentialsBinding
    @Inject lateinit var dataStoreManager : DataStoreManager
    @Inject lateinit var uiSupport : UiSupport
    @Inject lateinit var firebaseAdmin :FirebaseAdmin

    private lateinit var apiKey : String
    private lateinit var idApp : String
    private lateinit var dataBaseUrl : String
    private lateinit var mail : String
    private lateinit var pass : String

    private fun validateIdApp(idApp: String): Boolean {
        val regex = Regex("^\\d:\\d{12}:android:[a-f0-9]{22}$")
        return idApp.matches(regex)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InitFirebaseCredentialsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {

            setStringsCredentials()

            if(validateIdApp(idApp)){
                val firebaseApp = firebaseAdmin.getFirebaseApp(this, apiKey, idApp, dataBaseUrl)
                val auth = Firebase.auth(firebaseApp)

                auth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(this) { task ->
                    if (!task.isSuccessful) {
                        uiSupport.showErrorAlertDialog(this,
                            "Error",task.exception?.message ?: "Unknown error"
                        )
                    } else {
                        validateDataBase(firebaseApp, auth)
                    }
                }
            }
            else{
                uiSupport.showErrorAlertDialog(this,
                    "Error", "Oops! It seems there's an issue with your application ID. Please double-check the ID you've provided and ensure it matches the required format.")
            }
        }

        binding.btnLogin.setOnClickListener {

            setStringsCredentials()
            if(validateIdApp(idApp)){
                val firebaseApp = firebaseAdmin.getFirebaseApp(this, apiKey, idApp, dataBaseUrl)
                val auth = Firebase.auth(firebaseApp)

                auth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener { task ->
                    if (!task.isSuccessful){
                        uiSupport.showErrorAlertDialog(this,"Error", task.exception?.message ?: "Unknown error")
                    }
                    else{
                        validateDataBase(firebaseApp, auth)
                    }
                }
            }
            else{
                uiSupport.showErrorAlertDialog(this,
                    "Error", "Oops! It seems there's an issue with your application ID. Please double-check the ID you've provided and ensure it matches the required format.")
            }


        }

    }

    private fun validateDataBase(firebaseApp:FirebaseApp, auth:FirebaseAuth) {

        val intent = Intent(this, MainActivity::class.java)
        var test : String? = null
        val activity = this
        val dataBase = Firebase.database(firebaseApp)
        val refDatabase = dataBase.reference

        refDatabase.child(auth.uid.toString()+"/test").get().addOnSuccessListener {
            test = it.value.toString()
        }
        lifecycleScope.launch(Dispatchers.IO) {
            delay(5000)
            withContext(Dispatchers.Main){
                if(test!=null){
                    startActivity(intent)
                    finishAffinity()
                }
                else{
                    dataBase.goOffline()
                    uiSupport.showErrorAlertDialog(activity,
                        "Timeout", "The connection to the database has timed out, and access to data is currently unavailable. Please verify that the database URL is correct or check your internet connection")
                }
            }
        }
    }

    private fun setStringsCredentials() {

        /*apiKey = binding.txtFirebaseApiKey.text.toString()
        idApp = binding.txtApplicationId.text.toString()
        dataBaseUrl = binding.txtFirebaseDbUrl.text.toString()
        mail = binding.txtFirebaseAuthMail.text.toString()
        pass = binding.txtFirebaseAuthPassword.text.toString()*/

        /*Test credentials*/

        apiKey = "AIzaSyBO3mCBXGffV5ahQHyr4N21bF0fANX99tk"
        idApp = "1:683460994618:android:34079fa32c72165c903703"
        dataBaseUrl = "https://thingshub-577fc-default-rtdb.firebaseio.com/"
        mail = "correodeprueba1@thingshub.com"
        pass = "123456789"
    }

}