package com.btb.explorebangladesh.presentation.fragments.auth.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.btb.explorebangladesh.*
import com.btb.explorebangladesh.activity.toast
import com.btb.explorebangladesh.databinding.FragmentLoginBinding
import com.btb.explorebangladesh.view.hide
import com.btb.explorebangladesh.view.isVisible
import com.btb.explorebangladesh.view.show
import com.btb.explorebangladesh.presentation.fragments.base.BaseFragment
import com.btb.explorebangladesh.lang.LanguageChangeListener
import com.btb.explorebangladesh.lang.LanguageProvider
import com.btb.explorebangladesh.presentation.activities.main.MainActivity
import com.btb.explorebangladesh.presentation.fragments.base.BaseViewModel
import com.btb.explorebangladesh.task.GoogleSigningTaskContract
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.OAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>(
    R.layout.fragment_login
) {

    @Inject
    lateinit var languageProvider: LanguageProvider

    @Inject
    lateinit var fbCallbackManager: CallbackManager

    @Inject
    lateinit var googleSigningTaskContract: GoogleSigningTaskContract

    @Inject
    lateinit var twitterAuthProviderBuilder: OAuthProvider.Builder

    private var languageChangeListener: LanguageChangeListener? = null

    private var googleSigningResultLauncher: ActivityResultLauncher<Any?>? = null
    private var facebookSigningResultLauncher: ActivityResultLauncher<Collection<String>?>? = null

    companion object {
        private const val TAG = "LoginFragment"
    }

    override val viewModel by viewModels<LoginViewModel>()

    override fun initializeViewBinding(view: View) = FragmentLoginBinding.bind(view)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            languageChangeListener = context as LanguageChangeListener?
        } catch (e: Exception) {
            Log.e(TAG, "onAttach: ${e.message}")
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSpinnerLanguageAdapter()
        setupListeners()
        subscribeObservers()
        registerGoogleSigningTask()
        startAccessTokenTracker()

        if (BuildConfig.DEBUG) {
            binding.etEmail.setText("email1@demo.com")
            binding.etPassword.setText("12345678")
        }
    }




    override fun onDestroyView() {
        viewModel.stopFacebookTracker()
        super.onDestroyView()
    }

    private fun startAccessTokenTracker() {
        viewModel.startFacebookTracker()
    }

    private fun registerGoogleSigningTask() {

        facebookSigningResultLauncher = registerForActivityResult(
            LoginManager.getInstance().FacebookLoginActivityResultContract(
                fbCallbackManager, getString(R.string.facebook_app_id)
            )
        ){
            fbCallbackManager.onActivityResult(it.requestCode, it.resultCode, it.data)
        }


        googleSigningResultLauncher = registerForActivityResult(
            googleSigningTaskContract,
            viewModel.googleResultCallback
        )
    }

    private fun subscribeObservers() {
        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                showError(error)
            } else {
                binding.tvError.hide()
            }
        }

        viewModel.validationEvent.observe(viewLifecycleOwner) { event ->
            when (event) {
                is ValidationEvent.Failure -> {
                    showError(event.text)
                }
                ValidationEvent.Success -> {
                    binding.tvError.text = ""
                    navigateToMainFragment()
                }
            }
        }
    }

    private fun showError(text: UiText) {
        binding.tvError.text = text.asString(requireContext())
        if (!binding.tvError.isVisible()) {
            binding.tvError.show()
        }
    }

    private fun setupListeners() {
        binding.etEmail.addTextChangedListener { text: Editable? ->
            text?.toString()?.let {
                viewModel.onLoginEvent(LoginFormEvent.EmailChanged(it))
            }
        }

        binding.etPassword.addTextChangedListener { text: Editable? ->
            text?.toString()?.let {
                viewModel.onLoginEvent(LoginFormEvent.PasswordChanged(it))
            }
        }

        binding.btnLogin.setOnClickListener {
            viewModel.onLoginEvent(LoginFormEvent.Submit)
        }

        binding.relGoogleSignIn.setOnClickListener {
//            googleSigningResultLauncher?.launch(null)
            requireActivity().toast(R.string.comming_soon)
        }

        binding.relFacebookSignIn.setOnClickListener {
            facebookSigningResultLauncher?.launch(
                listOf("email", "public_profile")
            )
        }

        binding.relTwitterSignIn.setOnClickListener {
//            viewModel.startActivityForTwitterSignIn(requireActivity())
            requireActivity().toast(R.string.comming_soon)
        }

        binding.relLinkedInSignIn.setOnClickListener {
            requireActivity().toast(R.string.comming_soon)
        }

        binding.btnRegistration.setOnClickListener {
            navigateToRegistrationFragment()
        }

        binding.btnForgotPassword.setOnClickListener {
            navigateToForgotPasswordFragment()
        }

        binding.tvTerms.setOnClickListener {
            navigateToTermsAndServiceFragment()
        }

    }

    private fun loginWithFacebook() {
        LoginManager.getInstance()
            .logInWithReadPermissions(
                this,
                fbCallbackManager,
                listOf("email", "public_profile")
            )
//        LoginManager.getInstance().FacebookLoginActivityResultContract()
        LoginManager.getInstance().registerCallback(
            fbCallbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
//                    handleFacebookSignIn(result.accessToken)
                }

                override fun onCancel() {
//                    _error.postValue(UiText.StringResource(R.string.cancel_by_user))
                }

                override fun onError(error: FacebookException) {
                    Log.e(BaseViewModel.TAG, "onError: ${error.message}")
//                    _error.postValue(UiText.StringResource(R.string.failed_signing_facebook))
                }
            }
        )
    }

    private fun navigateToTermsAndServiceFragment() {
        findNavController().navigate(
            LoginFragmentDirections.actionLoginFragmentToWebViewFragment(
                StaticPage.TermsCondition
            )
        )
    }

    private fun navigateToForgotPasswordFragment() {
        findNavController().navigate(
            LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment()
        )
    }

    private fun navigateToRegistrationFragment() {
        findNavController().navigate(
            LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        )
    }

    private fun navigateToMainFragment() {
        requireActivity().startActivity(
            Intent(requireActivity(), MainActivity::class.java)
        )
        requireActivity().finish()
    }

    private fun setupSpinnerLanguageAdapter() {
        ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            arrayOf("English", "বাংলা")
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spLanguage.adapter = it
        }

        binding.spLanguage.setSelection(
            getLanguagePosition()
        )

        binding.spLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val langCode = if (position == 0) {
                    LanguageProvider.LANG_CODE_EN
                } else {
                    LanguageProvider.LANG_CODE_BN
                }
                if (languageProvider.getCurrentLanguage() != langCode) {
                    languageChangeListener?.onLanguageChange(langCode)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) = Unit
        }

    }


    private fun getLanguagePosition(): Int {
        return if (languageProvider.getCurrentLanguage() == LanguageProvider.LANG_CODE_EN) 0 else 1
    }

}