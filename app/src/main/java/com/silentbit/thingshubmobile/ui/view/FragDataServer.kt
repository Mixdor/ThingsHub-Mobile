package com.silentbit.thingshubmobile.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.silentbit.thingshubmobile.R
import com.silentbit.thingshubmobile.data.DataStoreManager
import com.silentbit.thingshubmobile.data.firebase.FirebaseAdmin
import com.silentbit.thingshubmobile.databinding.FragDataServerBinding
import com.silentbit.thingshubmobile.support.LayoutLinearProvider
import com.silentbit.thingshubmobile.support.SpanBuilder
import com.silentbit.thingshubmobile.support.TextViewBuilder
import com.silentbit.thingshubmobile.support.UiSupport
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class FragDataServer : Fragment() {

    private var _binding : FragDataServerBinding? = null
    private val binding get() = _binding!!
    @Inject lateinit var firebaseAdmin : FirebaseAdmin
    @Inject lateinit var dataStoreManager : DataStoreManager
    @Inject lateinit var textViewBuilder : TextViewBuilder
    @Inject lateinit var spanBuilder : SpanBuilder
    @Inject lateinit var uiSupport : UiSupport
    @Inject lateinit var layoutLinearProvider: LayoutLinearProvider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragDataServerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch(Dispatchers.IO) {
            val typeServer = dataStoreManager.loadTypeServer()
            withContext(Dispatchers.Main){

                when(typeServer){
                    getString(R.string.firebase) -> showCredentialsFirebase()
                }

            }
        }

    }

    private fun showCredentialsFirebase() {
        binding.linearLayoutCardServer.addView(
            textViewBuilder.getTitle(requireContext(), getString(R.string.firebase), true),
            layoutLinearProvider.getMatchWrap(16)
        )

        lifecycleScope.launch(Dispatchers.IO) {
            val fbCredentials = dataStoreManager.loadFirebaseCredentials()
            withContext(Dispatchers.Main) {

                binding.linearLayoutCardServer.addView(
                    textViewBuilder.getSubTitle(
                        requireContext(),
                        getString(R.string.application_id),
                        true
                    ),
                    layoutLinearProvider.getWraps(8, 0)
                )

                binding.linearLayoutCardServer.addView(
                    textViewBuilder.getText(requireContext(), fbCredentials.appId, true),
                    layoutLinearProvider.getWraps(8, 8, 8, 16)
                )

                binding.linearLayoutCardServer.addView(
                    textViewBuilder.getSubTitle(
                        requireContext(),
                        getString(R.string.database_url),
                        true
                    ),
                    layoutLinearProvider.getWraps(8, 8, 8, 0)
                )

                binding.linearLayoutCardServer.addView(
                    textViewBuilder.getText(requireContext(), fbCredentials.databaseUrl, true),
                    layoutLinearProvider.getWraps(8, 8, 8, 16)
                )

                binding.linearLayoutCardServer.addView(
                    textViewBuilder.getSubTitle(
                        requireContext(),
                        getString(R.string.api_key),
                        true
                    ),
                    layoutLinearProvider.getWraps(8, 8, 8, 0)
                )

                val showApi = textViewBuilder.getText(requireContext(), "[Show]", false)
                showApi.text = spanBuilder.getSpannableUnder("[Show]")
                showApi.setPadding(0, 8, 32, 16)
                showApi.setOnClickListener {
                    if (showApi.text.toString() == "[Show]") {
                        showFirebaseAuthDialog(showApi)
                    }
                }
                binding.linearLayoutCardServer.addView(
                    showApi,
                    layoutLinearProvider.getWraps(8, 0, 0, 8)
                )

            }
        }
    }

    private fun showFirebaseAuthDialog(showApi:TextView) {
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.request_password, null)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.authentication_required))
            .setMessage(getString(R.string.enter_admin_password))
            .setView(dialogView)
            .setPositiveButton(getString(R.string.done)){ dialog, _ ->

                val inputPassword = dialogView.findViewById<TextInputEditText>(R.id.txtDialogFirebaseAuthPassword)

                if(inputPassword.text.toString()!=""){
                    lifecycleScope.launch(Dispatchers.IO) {
                        val credentials = dataStoreManager.loadFirebaseCredentials()
                        val mail = credentials.mail
                        withContext(Dispatchers.Main){

                            val authApp = Firebase.auth(dataStoreManager.loadFirebaseApp())

                            authApp.signInWithEmailAndPassword(mail,inputPassword.text.toString()).addOnCompleteListener {task ->
                                if (task.isSuccessful){
                                    lifecycleScope.launch(Dispatchers.IO) {
                                        val resultApiKey = dataStoreManager.loadFirebaseCredentials().apiKey
                                        withContext(Dispatchers.Main){
                                            showApi.text = resultApiKey
                                            showApi.setTextIsSelectable(true)
                                            showApi.isClickable
                                        }
                                    }
                                }
                                else{
                                    uiSupport.showErrorAlertDialog(requireActivity(), getString(R.string.error), task.exception?.message ?: getString(R.string.unknown_error))
                                }
                            }
                        }
                    }
                }
                dialog.dismiss()
            }
            .show()
    }

}