package com.btb.explorebangladesh.data.remote.source

import com.btb.explorebangladesh.data.model.user_info.info_update.InfoUpdateRequest
import com.btb.explorebangladesh.data.remote.api.BtbApiService
import com.btb.explorebangladesh.data.remote.request.RegistrationRequest
import com.btb.explorebangladesh.middleware.SafeApiRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody


class BtbDataSourceImpl(
    private val apiService: BtbApiService
) : SafeApiRequest(), BtbDataSource {

    override suspend fun getCategories(queryMap: Map<String, Int>) = apiRequest {
        apiService.getCategories(queryMap)
    }

    override suspend fun getArticles(queryMap: Map<String, Any>) = apiRequest {
        apiService.getArticles(queryMap)
    }

    override suspend fun getArticleDetail(id: Int, lang: String) = apiRequest {
        apiService.getArticleDetail(id, lang)
    }

    override suspend fun isWishListed(id: Int) = apiRequest {
        apiService.isWishListed(id)
    }

    override suspend fun updateWishListed(id: Int, body: Map<String, Int>) = apiRequest {
        apiService.updateWishListed(id, body)
    }

    override suspend fun shareArticle(id: Int, body: Map<String, Int>) = apiRequest {
        apiService.shareArticle(id, body)
    }

    override suspend fun getWishList(queryMap: Map<String, Any>) = apiRequest {
        apiService.getWishList(queryMap)
    }

    override suspend fun getStaticDetail(page: String, lang: String) = apiRequest {
        apiService.getStaticDetail(page, lang)
    }


    override suspend fun getComments(queryMap: Map<String, Int>) = apiRequest {
        apiService.getComments(queryMap)
    }

    override suspend fun createComment(comment: String, articleId: Int) = apiRequest {
        apiService.createComment(comment, articleId)
    }


    override suspend fun getUserInformation(queryMap: Map<String, Any>) = apiRequest {
        apiService.getUserInformation(queryMap)
    }

    override suspend fun uploadProfileImage(imageUrl: RequestBody, fileName: RequestBody, file: MultipartBody.Part? ) = apiRequest {
        apiService.uploadProfilePhoto(imageUrl, fileName, file)
    }

    /*override suspend fun employeeInformationUpdate(body: InfoUpdateRequest) = apiRequest {
        apiService.employeeInformationUpdate(body)
    }*/

}