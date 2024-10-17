package com.silentbit.sensehubmobile.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import com.silentbit.sensehubmobile.R
import com.silentbit.sensehubmobile.data.DataStoreManager
import com.silentbit.sensehubmobile.data.firebase.FirebaseAdmin
import com.silentbit.sensehubmobile.databinding.InitFirebaseCredentialsBinding
import com.silentbit.sensehubmobile.support.UiSupport
import com.silentbit.sensehubmobile.ui.MainActivity
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
    @Inject lateinit var firebaseAdmin : FirebaseAdmin

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
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = InitFirebaseCredentialsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        screenSplash.setKeepOnScreenCondition{false}

        binding.btnRegister.setOnClickListener {

            setStringsCredentials()

            if(validateIdApp(idApp)){
                val firebaseApp = firebaseAdmin.getFirebaseApp(this, apiKey, idApp, dataBaseUrl)
                val auth = Firebase.auth(firebaseApp)

                auth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(this) { task ->
                    if (!task.isSuccessful) {
                        uiSupport.showErrorAlertDialog(this,
                            getString(R.string.error),task.exception?.message ?: getString(R.string.unknown_error)
                        )
                    } else {
                        validateDataBase(firebaseApp, auth)
                    }
                }
            }
            else{
                uiSupport.showErrorAlertDialog(this,
                    getString(R.string.error), getString(R.string.app_id_issue))
            }
        }

        binding.btnLogin.setOnClickListener {

            setStringsCredentials()
            if(validateIdApp(idApp)){
                val firebaseApp = firebaseAdmin.getFirebaseApp(this, apiKey, idApp, dataBaseUrl)
                val auth = Firebase.auth(firebaseApp)

                auth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener { task ->
                    if (!task.isSuccessful){
                        uiSupport.showErrorAlertDialog(this, getString(R.string.error), task.exception?.message ?: getString(R.string.unknown_error))
                    }
                    else{
                        validateDataBase(firebaseApp, auth)
                    }
                }
            }
            else{
                uiSupport.showErrorAlertDialog(this,
                    getString(R.string.error), getString(R.string.app_id_issue))
            }

        }

        binding.btnHelpApiKey.setOnClickListener{
            uiSupport.showHelpAlertDialog(this,
                getString(R.string.api_key), getString(R.string.current_key),
                R.layout.img_help_apikey
            )
        }

        binding.btnHelpAppId.setOnClickListener{
            uiSupport.showHelpAlertDialog(this,
                getString(R.string.application_id), getString(R.string.mobilesdk_app_id),
                R.layout.img_help_app_id
            )
        }

        binding.btnHelpDatabaseUrl.setOnClickListener {
            uiSupport.showHelpAlertDialog(this,
                getString(R.string.database_url), getString(R.string.root_database_url),
                R.layout.img_help_url_database
            )
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
                    dataStoreManager.saveTypeServer(getString(R.string.firebase))
                    dataStoreManager.saveFirebaseCredentials(
                        binding.txtFirebaseApiKey.text.toString(),
                        binding.txtApplicationId.text.toString(),
                        binding.txtFirebaseDbUrl.text.toString(),
                        binding.txtFirebaseAuthMail.text.toString()
                    )
                    startActivity(intent)
                    finishAffinity()
                }
                else{
                    dataBase.goOffline()
                    uiSupport.showErrorAlertDialog(activity,
                        getString(R.string.timeout), getString(R.string.database_timeout_error))
                }
            }
        }
    }

    private fun setStringsCredentials() {

        apiKey = binding.txtFirebaseApiKey.text.toString()
        idApp = binding.txtApplicationId.text.toString()
        dataBaseUrl = binding.txtFirebaseDbUrl.text.toString()
        mail = binding.txtFirebaseAuthMail.text.toString()
        pass = binding.txtFirebaseAuthPassword.text.toString()

        /*Test credentials*/
        /*apiKey = "AIzaSyAwoZ5VVk0Dh4jG5I7mwglZmSVisBxR0FM"
        idApp = "1:911681667162:android:60f0ddea565b57606ed863"
        dataBaseUrl = "https://test3ef78-default-rtdb.firebaseio.com/"
        mail = "correodeprueba1@thingshub.com"
        pass = "123456789"*/
    }

}