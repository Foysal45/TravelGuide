package com.btb.explorebangladesh.presentation.fragments.auth.login

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.btb.explorebangladesh.*
import com.btb.explorebangladesh.data.remote.request.LoginRequest
import com.btb.explorebangladesh.data.remote.request.SocialLoginRequest
import com.btb.explorebangladesh.domain.repository.AuthRepository
import com.btb.explorebangladesh.domain.usecases.auth.ValidateEmail
import com.btb.explorebangladesh.domain.usecases.auth.ValidatePassword
import com.btb.explorebangladesh.domain.usecases.auth.ValidateSocialType
import com.btb.explorebangladesh.presentation.fragments.base.BaseViewModel
import com.btb.explorebangladesh.util.UserFactory
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateEmail: ValidateEmail,
    private val validateSocialType: ValidateSocialType,
    private val validatePassword: ValidatePassword,
    private val repository: AuthRepository,
    private val userFactory: UserFactory,
    private val twitterAuthProviderBuilder: OAuthProvider.Builder,
    private val googleSigningClient: GoogleSignInClient
) : BaseViewModel() {

    private val email = MutableLiveData<String>()
    private val password = MutableLiveData<String>()

    private val socialEmail = MutableLiveData<String>()
    private val firstName = MutableLiveData<String>()
    private val accessToken = MutableLiveData<String>()
    private val socialType = MutableLiveData<String>()
    private val profileUrl = MutableLiveData<String>()


    private val _validationEvent = MutableLiveData<ValidationEvent>()
    val validationEvent: LiveData<ValidationEvent>
        get() = _validationEvent

    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }



    val googleResultCallback = ActivityResultCallback<Task<GoogleSignInAccount>?> { task ->
        if (task == null) {
            _error.postValue(UiText.StringResource(R.string.failed_signing_google))
        } else {
            task.addOnCanceledListener {
                _error.postValue(UiText.StringResource(R.string.cancel_by_user))
            }
            task.addOnFailureListener {
                Log.e(TAG, "handleGoogleSignInAccount : ${it.message}", )
                _error.postValue(UiText.StringResource(R.string.failed_signing_google))
            }
            task.addOnSuccessListener {
                handleGoogleSignInAccount(it)
            }
        }
    }

    private var fbAccessTokenTracker: AccessTokenTracker? = null

    fun startFacebookTracker(){
        fbAccessTokenTracker = object : AccessTokenTracker(){
            override fun onCurrentAccessTokenChanged(
                oldAccessToken: AccessToken?,
                currentAccessToken: AccessToken?
            ) {
                currentAccessToken ?: return
                Log.e(TAG, "onCurrentProfileChanged: ${currentAccessToken.token}")
                handleFacebookSignIn(currentAccessToken)
            }
        }

    }

    fun stopFacebookTracker(){
        fbAccessTokenTracker?.stopTracking()
        fbAccessTokenTracker = null
    }





    private fun handleFacebookSignIn(token: AccessToken) {
        val profileRequest = GraphRequest.newMeRequest(
            token
        ) { jsonObject, _ ->
            jsonObject ?: return@newMeRequest

            val facebookId = if (jsonObject.has("id")) jsonObject.getString("id") else null
            val name1 =
                if (jsonObject.has("first_name")) jsonObject.getString("first_name") else null

            val name2 =
                if (jsonObject.has("last_name")) jsonObject.getString("last_name") else null

            val fullName = "${name1 ?: ""} ${name2 ?: ""}"
            val accountId = facebookId ?: ""
            val emailAddress =
                if (jsonObject.has("email")) jsonObject.getString("email") else "$accountId@facebook.com"

            val profilePicture = "https://graph.facebook.com/$facebookId/picture?type=large"
            if (emailAddress.isEmpty()) {
                _error.postValue(UiText.StringResource(R.string.facebook_email_not_found))
                return@newMeRequest
            }

            socialType.value = SocialType.Facebook.type
            socialEmail.value = emailAddress
            firstName.value = fullName
            accessToken.value = facebookId
            profileUrl.value = profilePicture

            LoginManager.getInstance().logOut()
            doSocialSignIn()
        }
        val parameters = Bundle()
        parameters.putString("fields", "id, first_name, last_name, name, email")
        profileRequest.parameters = parameters
        profileRequest.executeAsync()

    }


    private fun handleGoogleSignInAccount(account: GoogleSignInAccount) {

        try {
            val emailAddress = account.email ?: ""
            val profilePic = account.photoUrl?.toString() ?: ""
            val name = account.displayName ?: ""

            socialType.value = SocialType.Google.type
            socialEmail.value = emailAddress
            firstName.value = name
            accessToken.value = account.idToken ?: "GOOGLE_ACCESS_TOKEN_NOT_FOUND"
            profileUrl.value = profilePic


            Log.e(TAG, "handleGoogleSignInAccount: ${accessToken.value}")
            Log.e(TAG, "handleGoogleSignInAccount: ${account.id}")

            googleSigningClient.signOut()
            doSocialSignIn()
        } catch (e: Exception) {
            _error.postValue(UiText.StringResource(R.string.failed_signing_google))
        }
    }

    fun startActivityForTwitterSignIn(activity: Activity) {
        firebaseAuth.startActivityForSignInWithProvider(activity, twitterAuthProviderBuilder.build())
            .addOnSuccessListener { authResult ->
                val profile = authResult.additionalUserInfo?.profile
                profile ?: return@addOnSuccessListener
                Log.e(TAG, "startActivityForTwitterSignIn: ${profile.values}")
                Firebase.auth.signOut()
            }
            .addOnFailureListener {
                Log.e(TAG, "startActivityForTwitterSignIn: ${it.message}")
                _error.postValue(UiText.StringResource(R.string.failed_signing_twitter))
            }
    }


    fun onLoginEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.EmailChanged -> {
                email.value = event.email
            }
            is LoginFormEvent.PasswordChanged -> {
                password.value = event.password
            }
            LoginFormEvent.Submit -> {
                doLogin()
            }
        }
    }

    private fun doLogin() {
        val emailResult = validateEmail(email.value ?: "")
        val passwordResult = validatePassword(password.value ?: "")

        if (!emailResult.isSuccessful) {
            _error.postValue(emailResult.errorMessage)
            return
        }

        if (!passwordResult.isSuccessful) {
            _error.postValue(passwordResult.errorMessage)
            return
        }

        val loginRequest = LoginRequest(
            email = email.value ?: "",
            password = password.value ?: ""
        )
        viewModelScope.launch {
            when (val resource = repository.doSignIn(
                loginRequest
            )) {
                is Resource.Failure -> {
                    updateLoading(false)
                    _validationEvent.postValue(
                        ValidationEvent.Failure(
                            resource.text,
                            resource.code
                        )
                    )
                }
                is Resource.Loading -> {
                    updateLoading(true)
                }
                is Resource.Success -> {
                    updateLoading(false)
                    saveTokenAndRequest(resource.data.accessToken, loginRequest)
                    _validationEvent.postValue(ValidationEvent.Success)
                }
            }
        }
    }

    private fun doSocialSignIn() {
        val email = socialEmail.value ?: ""
        val socialType = socialType.value ?: ""
        val firstName = firstName.value ?: ""
        val accessToken = accessToken.value ?: ""
        val profileUrl = profileUrl.value ?: ""
        val emailResult = validateEmail(email)
        val socialTypeResult = validateSocialType(socialType)

        if (!emailResult.isSuccessful) {
            _error.postValue(emailResult.errorMessage)
            return
        }

        if (!socialTypeResult.isSuccessful) {
            _error.postValue(socialTypeResult.errorMessage)
            return
        }
        val socialSignInRequest = SocialLoginRequest(
            email = email, firstName = firstName,
            lastName = firstName, socialType = socialType,
            profileUrl = profileUrl, accessToken = accessToken
        )
        viewModelScope.launch {
            when (val resource = repository.doSocialSignIn(socialSignInRequest)) {
                is Resource.Failure -> {
                    updateLoading(false)
                    _validationEvent.postValue(
                        ValidationEvent.Failure(
                            resource.text,
                            resource.code
                        )
                    )
                }
                is Resource.Loading -> {
                    updateLoading(true)
                }
                is Resource.Success -> {
                    updateLoading(false)
                    saveTokenAndRequest(resource.data.accessToken, LoginRequest(email, ""))
                    _validationEvent.postValue(ValidationEvent.Success)
                }
            }
        }
    }

    private fun saveTokenAndRequest(token: String, loginRequest: LoginRequest) {
        userFactory.saveAccessToken(token)
        userFactory.saveLoginRequest(loginRequest)
    }


}