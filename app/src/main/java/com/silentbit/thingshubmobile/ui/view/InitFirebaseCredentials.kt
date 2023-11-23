package com.silentbit.thingshubmobile.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.Firebase
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InitFirebaseCredentialsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, MainActivity::class.java)


        binding.btnRegister.setOnClickListener {

            setStringsCredentials()
            val firebaseApp = firebaseAdmin.getFirebaseApp(this, apiKey, idApp, dataBaseUrl)
            val auth = Firebase.auth(firebaseApp)

            auth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(this) { task ->
                if (!task.isSuccessful) {
                    firebaseApp.delete()
                    uiSupport.showErrorAlertDialog(this,
                        "Error",task.exception?.message ?: "Unknown error"
                    )
                } else {
                    //updateUI(null)
                }
            }
        }

        binding.btnLogin.setOnClickListener {

            setStringsCredentials()
            val firebaseApp = firebaseAdmin.getFirebaseApp(this, apiKey, idApp, dataBaseUrl)
            val auth = Firebase.auth(firebaseApp)

            auth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener { task ->
                if (!task.isSuccessful){
                    firebaseApp.delete()
                    uiSupport.showErrorAlertDialog(this,"Error", task.exception?.message ?: "Unknown error")
                }
                else{
                    var test : String? = null
                    val activity = this
                    val refDatabase = Firebase.database(firebaseApp).reference

                    refDatabase.child(auth.uid.toString()+"/test").get().addOnSuccessListener {
                        test = it.value.toString()
                    }
                    lifecycleScope.launch(Dispatchers.IO) {
                        delay(5000)
                        withContext(Dispatchers.Main){
                            if(test!=null){
                                Log.e("Test", test.toString())
                                startActivity(intent)
                                finishAffinity()
                            }
                            else{
                                firebaseApp.delete()
                                uiSupport.showErrorAlertDialog(activity,
                                    "Timeout", "The connection to the database has timed out, and access to data is currently unavailable. Please verify that the database URL is correct or check your internet connection")
                            }
                        }
                    }

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
    }

}