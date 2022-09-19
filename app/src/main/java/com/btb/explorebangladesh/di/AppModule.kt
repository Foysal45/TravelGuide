package com.btb.explorebangladesh.di

import android.content.Context
import com.btb.explorebangladesh.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.OAuthProvider
import com.google.gson.Gson
import com.btb.explorebangladesh.util.UserFactory
import com.btb.explorebangladesh.SharedPref
import com.btb.explorebangladesh.lang.LanguageProvider
import com.btb.explorebangladesh.task.GoogleSigningTaskContract
import com.btb.explorebangladesh.theme.ThemeProvider
import com.facebook.CallbackManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideSharedPref(
        @ApplicationContext context: Context
    ): SharedPref = SharedPref(
        context
    )

    @Provides
    @Singleton
    fun provideUserFactory(
        sharedPref: SharedPref,
        gson: Gson
    ): UserFactory = UserFactory(sharedPref, gson)

    @Provides
    @Singleton
    fun provideLanguageProvider(
        sharedPref: SharedPref
    ): LanguageProvider = LanguageProvider(sharedPref)

    @Provides
    @Singleton
    fun provideThemeProvider(
        sharedPref: SharedPref
    ): ThemeProvider = ThemeProvider(sharedPref)


    @Provides
    @Singleton
    fun provideGoogleSignInOptions(
        @ApplicationContext context: Context
    ): GoogleSignInOptions = GoogleSignInOptions
        .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.server_client_id))
        .requestEmail()
        .requestProfile()
        .build()

    @Provides
    @Singleton
    fun provideGoogleSignInClient(
        @ApplicationContext context: Context,
        gso: GoogleSignInOptions
    ): GoogleSignInClient = GoogleSignIn.getClient(context, gso)

    @Provides
    @Singleton
    fun provideGoogleSigningTaskContract(
        googleSignInClient: GoogleSignInClient
    ): GoogleSigningTaskContract = GoogleSigningTaskContract(googleSignInClient)

    @Provides
    @Singleton
    fun provideOAuthProviderBuilder(
    ): OAuthProvider.Builder = OAuthProvider.newBuilder("twitter.com")

    @Provides
    @Singleton
    fun provideFacebookCallbackManager(
    ): CallbackManager = CallbackManager.Factory.create()

}