package com.btb.explorebangladesh.presentation

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.btb.explorebangladesh.di.WrapperEntryPoint
import com.btb.explorebangladesh.SharedPref
import com.btb.explorebangladesh.lang.LanguageProvider
import com.btb.explorebangladesh.theme.ThemeProvider
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BTB : MultiDexApplication() {

    @Inject
    lateinit var sharedPref: SharedPref

    @Inject
    lateinit var themeProvider: ThemeProvider

    lateinit var languageProvider: LanguageProvider

    override fun onCreate() {
        super.onCreate()
        languageProvider = EntryPointAccessors.fromApplication(this, WrapperEntryPoint::class.java)
            .languageProvider()

        val theme = themeProvider.getCurrentTheme()
        AppCompatDelegate.setDefaultNightMode(theme)
    }
}