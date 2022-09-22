package com.btb.explorebangladesh.presentation.fragments.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import com.btb.explorebangladesh.R
import com.btb.explorebangladesh.activity.startAndFinish
import com.btb.explorebangladesh.databinding.FragmentSettingsBinding
import com.btb.explorebangladesh.lang.LanguageChangeListener
import com.btb.explorebangladesh.lang.LanguageProvider
import com.btb.explorebangladesh.presentation.activities.auth.AuthActivity
import com.btb.explorebangladesh.presentation.fragments.auth.login.LoginFragment
import com.btb.explorebangladesh.presentation.fragments.base.BaseFragment
import com.btb.explorebangladesh.theme.ThemeProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : BaseFragment<SettingsViewModel, FragmentSettingsBinding>(
    R.layout.fragment_settings
) {

    @Inject
    lateinit var languageProvider: LanguageProvider

    @Inject
    lateinit var themeProvider: ThemeProvider


    private var languageChangeListener: LanguageChangeListener? = null


    override val viewModel by viewModels<SettingsViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            languageChangeListener = context as LanguageChangeListener?
        } catch (e: Exception) {
            Log.e(TAG, "onAttach: ${e.message}")
        }
    }

    override fun initializeViewBinding(view: View) = FragmentSettingsBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        updateDefaultInfo()
        setupListeners()
    }

    private fun updateDefaultInfo() {

        val theme = themeProvider.getCurrentTheme()

        binding.chipGroupTheme.check(
            when (theme) {
                AppCompatDelegate.MODE_NIGHT_NO -> {
                    R.id.lightTheme
                }
                AppCompatDelegate.MODE_NIGHT_YES -> {
                    R.id.darkTheme
                }
                else -> {
                    R.id.defaultTheme
                }
            }
        )

        val language = languageProvider.getCurrentLanguage()
        binding.chipGroupLanguage.check(
            when (language) {
                LanguageProvider.LANG_CODE_BN -> R.id.languageBangla
                LanguageProvider.LANG_CODE_EN -> R.id.languageEnglish
                else -> R.id.languageEnglish
            }
        )
    }

    private fun setupListeners() {
        binding.chipGroupTheme.setOnCheckedStateChangeListener { _, checkedIds ->
            val checkedId = checkedIds.firstOrNull() ?: return@setOnCheckedStateChangeListener
            val theme = when (checkedId) {
                R.id.defaultTheme -> {
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                }
                R.id.lightTheme -> {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
                R.id.darkTheme -> {
                    AppCompatDelegate.MODE_NIGHT_YES
                }
                else -> {
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                }
            }
            Log.e(TAG, "setupListeners: $theme")
            themeProvider.updateTheme(theme)
            AppCompatDelegate.setDefaultNightMode(theme)
        }

        binding.chipGroupLanguage.setOnCheckedStateChangeListener { _, checkedIds ->
            val checkedId = checkedIds.firstOrNull() ?: return@setOnCheckedStateChangeListener
            val language = when (checkedId) {
                R.id.languageBangla -> {
                    LanguageProvider.LANG_CODE_BN
                }
                R.id.languageEnglish -> {
                    LanguageProvider.LANG_CODE_EN
                }
                else -> {
                    LanguageProvider.LANG_CODE_EN
                }
            }
            languageChangeListener?.onLanguageChange(language)
        }

    }


}