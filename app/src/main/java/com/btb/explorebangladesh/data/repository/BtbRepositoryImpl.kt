package com.btb.explorebangladesh.data.repository


import android.util.Log
import androidx.lifecycle.liveData
import com.btb.explorebangladesh.R
import com.btb.explorebangladesh.Resource
import com.btb.explorebangladesh.StaticPage
import com.btb.explorebangladesh.UiText
import com.btb.explorebangladesh.data.mapper.*
import com.btb.explorebangladesh.data.remote.source.BtbDataSource
import com.btb.explorebangladesh.domain.model.Article
import com.btb.explorebangladesh.domain.model.Comment
import com.btb.explorebangladesh.domain.model.District
import com.btb.explorebangladesh.domain.repository.BtbRepository
import com.btb.explorebangladesh.responses.*
import com.btb.explorebangladesh.responses.ApiResponse.Companion.UNKNOWN_ERROR_CODE
import com.btb.explorebangladesh.util.AssetManager

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

}