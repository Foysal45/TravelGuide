package com.btb.explorebangladesh.di

import android.content.Context
import com.btb.explorebangladesh.BuildConfig
import com.btb.explorebangladesh.data.remote.api.AuthApiService
import com.btb.explorebangladesh.data.remote.api.BtbApiService
import com.btb.explorebangladesh.data.remote.middleware.ConnectivityInterceptorImpl
import com.btb.explorebangladesh.data.remote.middleware.TokenAuthenticator
import com.btb.explorebangladesh.data.remote.source.AuthDataSource
import com.btb.explorebangladesh.data.remote.source.AuthDataSourceImpl
import com.btb.explorebangladesh.data.remote.source.BtbDataSource
import com.btb.explorebangladesh.data.remote.source.BtbDataSourceImpl
import com.btb.explorebangladesh.data.repository.AuthRepositoryImpl
import com.btb.explorebangladesh.data.repository.BtbRepositoryImpl
import com.btb.explorebangladesh.di.annotation.Auth
import com.btb.explorebangladesh.di.annotation.Main
import com.btb.explorebangladesh.domain.repository.AuthRepository
import com.btb.explorebangladesh.domain.repository.BtbRepository
import com.btb.explorebangladesh.util.UserFactory
import com.btb.explorebangladesh.middleware.ConnectivityInterceptor
import com.btb.explorebangladesh.util.AssetManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideConnectivityInterceptor(
        @ApplicationContext context: Context
    ): ConnectivityInterceptor = ConnectivityInterceptorImpl(context)


    @Auth
    @Provides
    @Singleton
    fun provideOkHttpClient(
        connectivityInterceptor: ConnectivityInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(connectivityInterceptor)
        .build()


    @Auth
    @Provides
    @Singleton
    fun provideAuthApiService(
        @Auth okHttpClient: OkHttpClient
    ): AuthApiService = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(AuthApiService::class.java)


    @Provides
    @Singleton
    fun provideAuthDataSource(
        @Auth authApiService: AuthApiService
    ): AuthDataSource = AuthDataSourceImpl(authApiService)

    @Provides
    @Singleton
    fun provideAuthRepository(
        authDataSource: AuthDataSource
    ): AuthRepository = AuthRepositoryImpl(authDataSource)


    @Provides
    @Singleton
    fun provideTokenAuthenticator(
        userFactory: UserFactory,
        authRepository: AuthRepository
    ): TokenAuthenticator {
        return TokenAuthenticator(userFactory, authRepository)
    }


    @Main
    @Provides
    @Singleton
    fun provideMainOkHttpClient(
        connectivityInterceptor: ConnectivityInterceptor,
        authenticator: TokenAuthenticator
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(connectivityInterceptor)
        .authenticator(authenticator)
        .build()

    @Main
    @Provides
    @Singleton
    fun provideBtbApiService(
        @Main okHttpClient: OkHttpClient
    ): BtbApiService = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BtbApiService::class.java)

    @Provides
    @Singleton
    fun provideBtbDataSource(
        @Main apiService: BtbApiService
    ): BtbDataSource = BtbDataSourceImpl(apiService)

    @Provides
    @Singleton
    fun provideBtbRepository(
        assetManager: AssetManager,
        dataSource: BtbDataSource
    ): BtbRepository = BtbRepositoryImpl(assetManager, dataSource)



}