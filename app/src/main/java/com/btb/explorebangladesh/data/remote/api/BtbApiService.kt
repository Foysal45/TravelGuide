package com.btb.explorebangladesh.data.remote.api
import com.btb.explorebangladesh.data.model.CommentDto
import com.btb.explorebangladesh.data.model.user_info.UserInfoDto
import com.btb.explorebangladesh.data.model.user_info.info_update.InfoUpdateRequest
import com.btb.explorebangladesh.data.model.user_info.info_update.UserInfoUpdate
import com.btb.explorebangladesh.data.remote.responses.*
import com.btb.explorebangladesh.responses.ResponseDTO
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface BtbApiService {

    @GET("api/v1/categories")
    suspend fun getCategories(@QueryMap queryMap: Map<String, Int>): Response<ResponseDTO<CategoryData>>

    @GET("api/v1/articles")
    suspend fun getArticles(@QueryMap queryMap: Map<String, @JvmSuppressWildcards Any>): Response<ResponseDTO<ArticleData>>

    @GET("api/v1/articles/{id}")
    suspend fun getArticleDetail(@Path("id") id: Int, @Query("lang") lang: String): Response<ResponseDTO<ArticleData>>

    @GET("api/v1/articles/{id}/wish")
    suspend fun isWishListed(@Path("id") id: Int): Response<ResponseDTO<WishData>>

    @POST("api/v1/articles/{id}/wish")
    suspend fun updateWishListed(@Path("id") id: Int, @Body body: Map<String, Int>): Response<ResponseDTO<Any>>

    @POST("api/v1/articles/{id}/share")
    suspend fun shareArticle(@Path("id") id: Int, @Body body: Map<String, Int>): Response<ResponseDTO<Any>>

    @GET("api/v1/articles/wishlist")
    suspend fun getWishList(@QueryMap queryMap: Map<String, @JvmSuppressWildcards Any>): Response<ResponseDTO<ArticleData>>

    @GET("api/v1/static/{page}")
    suspend fun getStaticDetail(@Path("page") page: String, @Query("lang") lang: String): Response<ResponseDTO<StaticData>>

    @GET("api/v1/comments/article-wise-comment")
    suspend fun getComments(@QueryMap queryMap: Map<String, Int>): Response<ResponseDTO<CommentData>>

    @POST("api/v1/comments/create")
    @FormUrlEncoded
    suspend fun createComment(@Field("comments") comment: String, @Field("articleId") articleId: Int): Response<ResponseDTO<CommentDto>>

    @GET("api/v1/user/me") //user profile - user Information
    suspend fun getUserInformation(@QueryMap queryMap: Map<String, @JvmSuppressWildcards Any>): Response<ResponseDTO<UserInfoDto>>

    @Multipart
    @POST("files/write/upload") //user profile - user Image upload(update)
    suspend fun uploadProfilePhoto(@Part("ImageUrl") ImageUrl: RequestBody, @Part("FileName") FileName: RequestBody, @Part file: MultipartBody.Part? = null) : Response<String>

    @PATCH("api/v1/user/me") //user profile - user data update
    suspend fun employeeInformationUpdate(@Body body: InfoUpdateRequest): Response<ResponseDTO<UserInfoUpdate>>

}