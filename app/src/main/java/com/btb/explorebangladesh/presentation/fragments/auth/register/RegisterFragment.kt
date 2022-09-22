package com.btb.explorebangladesh.presentation.fragments.auth.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.btb.explorebangladesh.R
import com.btb.explorebangladesh.StaticPage
import com.btb.explorebangladesh.UiText
import com.btb.explorebangladesh.ValidationEvent
import com.btb.explorebangladesh.activity.toast
import com.btb.explorebangladesh.databinding.FragmentRegistrationBinding
import com.btb.explorebangladesh.presentation.fragments.auth.login.LoginFragmentDirections
import com.btb.explorebangladesh.presentation.fragments.base.BaseFragment
import com.btb.explorebangladesh.view.hide
import com.btb.explorebangladesh.view.isVisible
import com.btb.explorebangladesh.view.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<RegisterViewModel, FragmentRegistrationBinding>(
    R.layout.fragment_registration
) {
    override val viewModel by viewModels<RegisterViewModel>()

    override fun initializeViewBinding(view: View) = FragmentRegistrationBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        subscribeObservers()
    }

    private fun setupListeners() {
        binding.etName.addTextChangedListener { text ->
            text?.toString()?.let { name ->
                viewModel.onRegisterEvent(RegisterFormEvent.NameChanged(name))
            }
        }
        binding.etEmail.addTextChangedListener { text ->
            text?.toString()?.let { email ->
                viewModel.onRegisterEvent(RegisterFormEvent.EmailChanged(email))
            }
        }
        binding.etPhone.addTextChangedListener { text ->
            text?.toString()?.let { phone ->
                viewModel.onRegisterEvent(RegisterFormEvent.PhoneChanged(phone))
            }
        }
//        binding.ccp.setOnCountryChangeListener { text ->
//            text?.toString()?.let { code ->
//                viewModel.onRegisterEvent(RegisterFormEvent.CountryCode(code))
//            }
//        }

        binding.etCountryName.addTextChangedListener { text ->
            text?.toString()?.let { name ->
                viewModel.onRegisterEvent(RegisterFormEvent.CountryChanged(name))
            }
        }

        binding.etPassword.addTextChangedListener { text ->
            text?.toString()?.let { password ->
                viewModel.onRegisterEvent(RegisterFormEvent.PasswordChanged(password))
            }
        }

        binding.etConfirmPassword.addTextChangedListener { text ->
            text?.toString()?.let { password ->
                viewModel.onRegisterEvent(RegisterFormEvent.ConfirmPasswordChanged(password))
            }
        }

        binding.pinEntry.addTextChangedListener { text ->
            text?.toString()?.let { otp ->
                viewModel.onRegisterEvent(RegisterFormEvent.OtpChanged(otp))
            }
        }

        binding.btnSignUp.setOnClickListener {
            if (binding.checkBox.isChecked) {
                viewModel.onRegisterEvent(RegisterFormEvent.SignUp)
            }else{
                requireActivity().toast(R.string.select_terms_condition)
            }
        }

        binding.btnSubmit.setOnClickListener {
            viewModel.onRegisterEvent(RegisterFormEvent.Submit)
        }

        binding.tvTerms.setOnClickListener {
            navigateToTermsAndServiceFragment()
        }


    }

    private fun navigateToTermsAndServiceFragment() {
        findNavController().navigate(
            RegisterFragmentDirections.actionRegisterFragmentToWebViewFragment(
                StaticPage.TermsCondition
            )
        )
    }

    private fun subscribeObservers() {
        viewModel.registrationState.observe(viewLifecycleOwner) { state ->
            when (state) {
                RegistrationState.OtpVerificationState -> {
                    binding.llRegistrationFormContainer.hide()
                    binding.llOtpContainer.show()
                }
                RegistrationState.RegistrationFormState -> {
                    binding.llOtpContainer.hide()
                    binding.llRegistrationFormContainer.show()
                }
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            showErrorMessage(it)
        }
        viewModel.validationEvent.observe(viewLifecycleOwner) { event ->
            when (event) {
                is ValidationEvent.Failure -> {
                    showErrorMessage(event.text)
                }
                ValidationEvent.Success -> {
                    findNavController().navigateUp()
                }
            }
        }
    }

    private fun showErrorMessage(error: UiText?) {
        val text = error?.asString(requireContext())
        if (binding.llRegistrationFormContainer.isVisible()) {
            binding.tvError.text = text
            if (!binding.tvError.isVisible()) {
                binding.tvError.show()
            }
        }

        if (binding.llOtpContainer.isVisible()) {
            binding.tvOtpError.text = text
            if (!binding.tvOtpError.isVisible()) {
                binding.tvOtpError.show()
            }
        }
    }

}