package com.btb.explorebangladesh.domain.repository
import androidx.lifecycle.LiveData
import com.btb.explorebangladesh.Resource
import com.btb.explorebangladesh.StaticPage
import com.btb.explorebangladesh.data.model.user_info.info_update.InfoUpdateRequest
import com.btb.explorebangladesh.data.model.user_info.info_update.UserInfoUpdate
import com.btb.explorebangladesh.data.remote.api.BtbApiService
import com.btb.explorebangladesh.domain.model.*
import com.btb.explorebangladesh.domain.model.user_info.UserInfo
import com.btb.explorebangladesh.responses.ResponseDTO
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body

interface BtbRepository {

    fun getDistricts(): List<District>

    fun getCategories(queryMap: Map<String, Int>): LiveData<Resource<List<Category>>>

    suspend fun getArticles(queryMap: Map<String, Any>): Resource<List<Article>>

    suspend fun getArticleDetail(id: Int, lang: String): Resource<ArticleDetail>

    suspend fun isWishListed(id: Int): Resource<Boolean>

    suspend fun updateWishListed(id: Int, body: Map<String, Int>): Resource<Boolean>

    suspend fun shareArticle(id: Int, body: Map<String, Int>): Resource<Boolean>

    suspend fun getWishList(queryMap: Map<String, Any>): Resource<List<Article>>

    suspend fun getStaticDetail(page: StaticPage, lang: String): Resource<String>

    suspend fun getComments(queryMap: Map<String, Int>): Resource<List<Comment>>

    suspend fun createComment(comment: String, articleId: Int): Resource<Comment>

    suspend fun getUserInformation(queryMap: Map<String, Any>): Resource<UserInfo>

   suspend fun uploadProfileImage(imageUrl: RequestBody, fileName: RequestBody, file: MultipartBody.Part? ) : Resource<String>

  // suspend fun employeeInformationUpdate(@Body body: InfoUpdateRequest): Resource<UserInfoUpdate>

}