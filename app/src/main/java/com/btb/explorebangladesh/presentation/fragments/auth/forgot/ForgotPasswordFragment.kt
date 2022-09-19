package com.btb.explorebangladesh.presentation.fragments.auth.forgot

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.btb.explorebangladesh.R
import com.btb.explorebangladesh.StaticPage
import com.btb.explorebangladesh.databinding.FragmentForgotPasswordBinding
import com.btb.explorebangladesh.ValidationEvent
import com.btb.explorebangladesh.presentation.fragments.auth.register.RegisterFragmentDirections
import com.btb.explorebangladesh.presentation.fragments.base.BaseFragment
import com.btb.explorebangladesh.view.hide
import com.btb.explorebangladesh.view.isVisible
import com.btb.explorebangladesh.view.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<ForgotPasswordViewModel, FragmentForgotPasswordBinding>(
    R.layout.fragment_forgot_password
) {
    override val viewModel by viewModels<ForgotPasswordViewModel>()

    override fun initializeViewBinding(view: View) = FragmentForgotPasswordBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        setupListeners()
    }

    private fun setupListeners() {
        binding.etEmail.addTextChangedListener {
            it?.toString()?.let { text ->
                viewModel.onForgotEvent(ForgotFormEvent.EmailChanged(text))
            }
        }

        binding.pinEntry.addTextChangedListener {
            it?.toString()?.let { text ->
                viewModel.onForgotEvent(ForgotFormEvent.OtpChanged(text))
            }
        }

        binding.etPassword.addTextChangedListener {
            it?.toString()?.let { text ->
                viewModel.onForgotEvent(ForgotFormEvent.PasswordChanged(text))
            }
        }

        binding.etConfirmPassword.addTextChangedListener {
            it?.toString()?.let { text ->
                viewModel.onForgotEvent(ForgotFormEvent.ConfirmPasswordChanged(text))
            }
        }

        binding.btnContinue.setOnClickListener {
            viewModel.onForgotEvent(ForgotFormEvent.ContinueWithEmail)
        }

        binding.btnOtpContinue.setOnClickListener {
            viewModel.onForgotEvent(ForgotFormEvent.ContinueWithOtp)
        }

        binding.btnResetPassword.setOnClickListener {
            viewModel.onForgotEvent(ForgotFormEvent.ResetPassword)
        }


        binding.tvTerms.setOnClickListener {
            navigateToTermsAndServiceFragment()
        }

    }

    private fun navigateToTermsAndServiceFragment() {
        findNavController().navigate(
            ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToWebViewFragment(
                StaticPage.TermsCondition
            )
        )
    }

    private fun subscribeObservers() {

        viewModel.updateState(ForgotState.EmailState)

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                ForgotState.EmailState -> {
                    binding.llOtpContainer.hide()
                    binding.llResetPassword.hide()
                    binding.llEmailContainer.show()
                }
                ForgotState.OtpState -> {
                    binding.llResetPassword.hide()
                    binding.llEmailContainer.hide()
                    binding.llOtpContainer.show()
                }
                ForgotState.ResetState -> {
                    binding.llEmailContainer.hide()
                    binding.llOtpContainer.hide()
                    binding.llResetPassword.show()
                }
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            val text = it?.asString(requireContext())
            showError(text)
        }

        viewModel.validationEvent.observe(viewLifecycleOwner) { event ->
            when (event) {
                is ValidationEvent.Failure -> {
                    val text = event.text.asString(requireContext())
                    showError(text)
                }
                ValidationEvent.Success -> {
                    navigateToLoginFragment()
                }
            }
        }


    }

    private fun navigateToLoginFragment() {
        findNavController().navigateUp()
    }

    private fun showError(text: String?) {
        if (binding.llEmailContainer.isVisible()) {
            if (!binding.tvEmailError.isVisible()) {
                binding.tvEmailError.show()
            }
            binding.tvEmailError.text = text
        }
        if (binding.llOtpContainer.isVisible()) {
            if (!binding.tvOtpError.isVisible()) {
                binding.tvOtpError.show()
            }
            binding.tvOtpError.text = text
        }
        if (binding.llResetPassword.isVisible()) {
            if (!binding.tvConfirmPasswordError.isVisible()) {
                binding.tvConfirmPasswordError.show()
            }
            binding.tvConfirmPasswordError.text = text
        }
    }
}