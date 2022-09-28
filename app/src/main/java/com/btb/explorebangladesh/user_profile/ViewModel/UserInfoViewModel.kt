package com.btb.explorebangladesh.user_profile.ViewModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.btb.explorebangladesh.Resource
import com.btb.explorebangladesh.data.model.user_info.info_update.InfoUpdateRequest
import com.btb.explorebangladesh.domain.model.user_info.UserInfo
import com.btb.explorebangladesh.domain.repository.BtbRepository
import com.btb.explorebangladesh.lang.LanguageProvider
import com.btb.explorebangladesh.presentation.fragments.base.BaseViewModel
import com.btb.explorebangladesh.util.UserFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val repository: BtbRepository,
    private val userFactory: UserFactory,
    private val languageProvider: LanguageProvider
) : BaseViewModel() {

    private val name = MutableLiveData<String>()
    private val phone = MutableLiveData<String>()
    private val gender = MutableLiveData<String>()
    private val country = MutableLiveData<String>()
    private val postCode = MutableLiveData<String>()
    private val nationality = MutableLiveData<String>()
    private val email = MutableLiveData<String>()
    private val address = MutableLiveData<String>()
    private val countryCode = MutableLiveData<String>()

    val updateProfilePic = MutableLiveData<Boolean>(false)
    private val _articles = MutableLiveData<UserInfo>()
    val articles: LiveData<UserInfo>
        get() = _articles

    fun hasLoggedIn(): Boolean = userFactory.getAccessToken().isNotEmpty()
    private val responseData: MutableLiveData<String> = MutableLiveData()



    //function for fetch user information
    fun getUserInformation(): LiveData<UserInfo> {
        val language = languageProvider.getCurrentLanguage()
        _isLoading.postValue(true)
        val responseBody = MutableLiveData<UserInfo>()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getUserInformation(
                mapOf(
                    "lang" to language
                )
            )
            withContext(Dispatchers.Main) {
                when (response) {
                    is Resource.Failure -> {
                        _isLoading.postValue(false)
                        _error.postValue(response.text)
                    }
                    is Resource.Loading -> Unit
                    is Resource.Success -> {
                        //  Log.e(TAG, "fetchWishList: ${response.data}")
                        _isLoading.postValue(false)
                        _articles.postValue(response.data)
                    }
                }
            }

        }
        return responseBody
    }




//function for user profile Image upload
    fun uploadProfilePhoto(fileName: String, imagePath: String, fileUrl: String): LiveData<String> {

        val mediaTypeText = "text/plain".toMediaTypeOrNull()
        val mediaTypeMultipart = "multipart/form-data".toMediaTypeOrNull()
        val responseData: MutableLiveData<String> = MutableLiveData()

        viewModelScope.launch(Dispatchers.IO) {
            val fileNameR = fileName.toRequestBody(mediaTypeText)
            val imagePathR = imagePath.toRequestBody(mediaTypeText)/*
            val file = File(fileUrl)
            val requestFile = file.asRequestBody(mediaTypeMultipart)*/
            val compressedFile = File(fileUrl)
            val requestFile = compressedFile.asRequestBody(mediaTypeMultipart)
            val part = MultipartBody.Part.createFormData("img", fileName, requestFile)

            val responseBTB = repository.uploadProfileImage(fileNameR, imagePathR, part)

            withContext(Dispatchers.Main) {

                when (responseBTB) {
                    is Resource.Failure -> {
                        _isLoading.postValue(false)
                        _error.postValue(responseBTB.text)
                    }
                    is Resource.Loading -> Unit
                    is Resource.Success -> {
                        Log.e(TAG, "fetchWishList: ${responseBTB.data}")
                        _isLoading.postValue(false)
                        responseData.postValue(responseBTB.data)
                    }

                }
            }
        }
        return responseData
    }




    //function for update user information
   /* fun employeeInformationUpdate(requestBody: InfoUpdateRequest) {
     *//*   val name = name.value?: ""
        val phone = countryCode.value ?: ("" + phone.value) ?: ""
        val gender = gender.value?: ""
        val country = country.value?: ""
        val postCode = postCode.value?: ""
        val nationality = nationality.value?: ""
        val email = email.value?: ""
        val address = address.value?: ""


        val infoUpdateRequest = InfoUpdateRequest(
            fullName = name,
            phone = phone,
            gender = gender,
            country = country,
            postcode = postCode,
            nationality = nationality,
            email = email,
            address = address
        )*//*

        viewModelScope.launch {
            when (val response = repository.employeeInformationUpdate(requestBody)) {
                is Resource.Failure -> {
                    _isLoading.postValue(false)
                    _error.postValue(response.text)
                }
                is Resource.Loading -> Unit
                is Resource.Success -> {
                    Log.e(TAG, "fetchWishList: $response")
                    _isLoading.postValue(false)
                    responseData.postValue(response.data.toString())
                }
            }
        }

    }*/
}