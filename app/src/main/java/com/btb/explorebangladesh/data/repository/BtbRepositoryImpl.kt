package com.btb.explorebangladesh.data.repository
import androidx.lifecycle.liveData
import com.btb.explorebangladesh.R
import com.btb.explorebangladesh.Resource
import com.btb.explorebangladesh.StaticPage
import com.btb.explorebangladesh.UiText
import com.btb.explorebangladesh.data.mapper.*
import com.btb.explorebangladesh.data.model.user_info.info_update.InfoUpdateRequest
import com.btb.explorebangladesh.data.remote.request.RegistrationRequest
import com.btb.explorebangladesh.data.remote.source.BtbDataSource
import com.btb.explorebangladesh.domain.model.District
import com.btb.explorebangladesh.domain.repository.BtbRepository
import com.btb.explorebangladesh.responses.*
import com.btb.explorebangladesh.responses.ApiResponse.Companion.UNKNOWN_ERROR_CODE
import com.btb.explorebangladesh.util.AssetManager
import okhttp3.MultipartBody
import okhttp3.RequestBody

class BtbRepositoryImpl(
    private val assetManager: AssetManager,
    private val dataSource: BtbDataSource
) : BtbRepository {

    override fun getDistricts(): List<District> {
        return assetManager.getDistricts().map { it.toDistrict() }
    }

    override fun getCategories(queryMap: Map<String, Int>) = liveData {
        emit(Resource.Loading())
        emit(
            when (val response = dataSource.getCategories(queryMap)) {
                is ApiEmptyResponse -> Resource.Failure(response.text, response.code)
                is ApiErrorResponse -> Resource.Failure(response.text, response.code)
                is ApiSuccessResponse -> {
                    val dto = response.body.dto
                    if (dto != null) {
                        Resource.Success(dto.categories.map { it.toCategory() })
                    } else {
                        Resource.Failure(
                            text = UiText.StringResource(R.string.message_unknown_error),
                            code = ApiResponse.UNKNOWN_ERROR_CODE
                        )
                    }
                }
            }
        )
    }

    override suspend fun getArticles(queryMap: Map<String, Any>) =
        when (val response = dataSource.getArticles(queryMap)) {
            is ApiEmptyResponse -> Resource.Failure(response.text, response.code)
            is ApiErrorResponse -> Resource.Failure(response.text, response.code)
            is ApiSuccessResponse -> Resource.Success((
                    response.body.dto?.articles?.map { it.toArticle() } ?: emptyList()
                    ))
        }


    override suspend fun getArticleDetail(
        id: Int, lang: String
    ) = when (val response = dataSource.getArticleDetail(id, lang)) {
        is ApiEmptyResponse -> Resource.Failure(response.text, response.code)
        is ApiErrorResponse -> Resource.Failure(response.text, response.code)
        is ApiSuccessResponse -> {
            val dto = response.body.dto?.articleDetail
            if (dto != null) {
                Resource.Success(dto.toArticleDetail())
            } else {
                Resource.Failure(
                    UiText.StringResource(R.string.message_unknown_error),
                    UNKNOWN_ERROR_CODE
                )
            }
        }
    }

    override suspend fun isWishListed(
        id: Int
    ) = when (val response = dataSource.isWishListed(id)) {
        is ApiEmptyResponse -> Resource.Failure(response.text, response.code)
        is ApiErrorResponse -> Resource.Failure(response.text, response.code)
        is ApiSuccessResponse -> Resource.Success(response.body.status == 200)
    }

    override suspend fun updateWishListed(
        id: Int, body: Map<String, Int>
    ) = when (val response = dataSource.updateWishListed(id, body)) {
        is ApiEmptyResponse -> Resource.Failure(response.text, response.code)
        is ApiErrorResponse -> Resource.Failure(response.text, response.code)
        is ApiSuccessResponse -> Resource.Success(response.body.status == 200)
    }

    override suspend fun shareArticle(
        id: Int, body: Map<String, Int>
    ) = when (val response = dataSource.shareArticle(id, body)) {
        is ApiEmptyResponse -> Resource.Failure(response.text, response.code)
        is ApiErrorResponse -> Resource.Failure(response.text, response.code)
        is ApiSuccessResponse -> Resource.Success(response.body.status == 200)
    }

    override suspend fun getWishList(
        queryMap: Map<String, Any>
    ) = when (val response = dataSource.getWishList(queryMap)) {
        is ApiEmptyResponse -> Resource.Failure(response.text, response.code)
        is ApiErrorResponse -> Resource.Failure(response.text, response.code)
        is ApiSuccessResponse -> Resource.Success((
                response.body.dto?.wishes?.map { it.toArticle() } ?: emptyList()
                ))
    }

    override suspend fun getStaticDetail(
        page: StaticPage, lang: String
    ) = when (val response = dataSource.getStaticDetail(page.page, lang)) {
        is ApiEmptyResponse -> Resource.Failure(response.text, response.code)
        is ApiErrorResponse -> Resource.Failure(response.text, response.code)
        is ApiSuccessResponse -> {
            val dto = response.body.dto
            if (dto != null) {
                when (page) {
                    StaticPage.AboutUs -> Resource.Success(
                        response.body.dto?.aboutUs ?: ""
                    )
                    StaticPage.PrivacyPolicy -> Resource.Success(
                        response.body.dto?.privacyPolicy ?: ""
                    )
                    StaticPage.TermsCondition -> {
                        Resource.Success(
                            response.body.dto?.tramsCondition ?: ""
                        )
                    }
                    StaticPage.Unknown -> {
                        Resource.Failure(
                            UiText.StringResource(R.string.message_unknown_error),
                            UNKNOWN_ERROR_CODE
                        )
                    }
                }
            } else {
                Resource.Failure(
                    UiText.StringResource(R.string.message_unknown_error),
                    UNKNOWN_ERROR_CODE
                )
            }

        }
    }

    override suspend fun getComments(
        queryMap: Map<String, Int>
    ) = when (val response = dataSource.getComments(queryMap)) {
        is ApiEmptyResponse -> Resource.Failure(response.text, response.code)
        is ApiErrorResponse -> Resource.Failure(response.text, response.code)
        is ApiSuccessResponse -> Resource.Success((
                response.body.dto?.comments?.map { it.toComment() } ?: emptyList()
                ))
    }

    override suspend fun createComment(
        comment: String, articleId: Int
    ) = when (val response = dataSource.createComment(comment, articleId)) {
        is ApiEmptyResponse -> Resource.Failure(response.text, response.code)
        is ApiErrorResponse -> Resource.Failure(response.text, response.code)
        is ApiSuccessResponse -> {
            val dto = response.body.dto
            if (dto != null) {
                Resource.Success(dto.toComment())
            } else {
                Resource.Failure(
                    UiText.StringResource(R.string.message_unknown_error),
                    UNKNOWN_ERROR_CODE
                )
            }
        }
    }

    //function for fetch user information
    override suspend fun getUserInformation(queryMap: Map<String, Any>)
        = when (val response = dataSource.getUserInformation(queryMap))  {
        is ApiEmptyResponse -> Resource.Failure(response.text, response.code)
        is ApiErrorResponse -> Resource.Failure(response.text, response.code)

        is ApiSuccessResponse -> {
            val dto = response.body.dto
            if (dto != null) {
                Resource.Success(dto.toUserInfo())
            } else {
                Resource.Failure(
                    UiText.StringResource(R.string.message_unknown_error),
                    UNKNOWN_ERROR_CODE
                )
            }
        }

    }

    //function for user profile Image upload
    override suspend fun uploadProfileImage(imageUrl: RequestBody, fileName: RequestBody, file: MultipartBody.Part?)
            = when (val response = dataSource.uploadProfileImage(imageUrl,fileName,file))  {
        is ApiEmptyResponse -> Resource.Failure(response.text, response.code)
        is ApiErrorResponse -> Resource.Failure(response.text, response.code)

        is ApiSuccessResponse -> {
            val dto = response.body
            if (dto != null) {
                Resource.Success(dto)
            } else {
                Resource.Failure(
                    UiText.StringResource(R.string.message_unknown_error),
                    UNKNOWN_ERROR_CODE
                )
            }
        }

    }


    //function for update user information
   /* override suspend fun employeeInformationUpdate(body: InfoUpdateRequest)
        = when (val response = dataSource.employeeInformationUpdate(body)) {
        is ApiEmptyResponse -> Resource.Failure(response.text, response.code)
        is ApiErrorResponse -> Resource.Failure(response.text, response.code)

        is ApiSuccessResponse -> {
            val dto = response.body.dto
            if (dto != null) {
                Resource.Success(dto.payload.toUpdateUserInfo())
            } else {
                Resource.Failure(
                    UiText.StringResource(
                        R.string.message_unknown_error
                    ),
                    UNKNOWN_ERROR_CODE
                )
            }
        }
    }*/


}