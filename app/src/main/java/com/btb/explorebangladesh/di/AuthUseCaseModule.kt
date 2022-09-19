package com.btb.explorebangladesh.di

import com.btb.explorebangladesh.domain.usecases.auth.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AuthUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideValidateEmail(): ValidateEmail = ValidateEmail()

    @Provides
    @ViewModelScoped
    fun provideValidatePassword(): ValidatePassword = ValidatePassword()

    @Provides
    @ViewModelScoped
    fun provideValidateConfirmPassword(): ValidateConfirmPassword = ValidateConfirmPassword()

    @Provides
    @ViewModelScoped
    fun provideValidateName(): ValidateName = ValidateName()

    @Provides
    @ViewModelScoped
    fun provideValidateCountryName(): ValidateCountryName = ValidateCountryName()

    @Provides
    @ViewModelScoped
    fun provideValidateOtp(): ValidateOtp = ValidateOtp()

    @Provides
    @ViewModelScoped
    fun provideValidateSocialType(): ValidateSocialType = ValidateSocialType()



}