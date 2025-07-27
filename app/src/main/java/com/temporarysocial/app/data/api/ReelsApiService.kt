package com.temporarysocial.app.data.api

import com.temporarysocial.app.data.model.*
import retrofit2.Response
import retrofit2.http.*

/**
 * API Group 5: Reels/Shorts Video Feed
 * Base URL: https://x8ki-letl-twmt.n7.xano.io/api:Yb9nMimD
 */
interface ReelsApiService {
    
    @GET("feed")
    suspend fun getFeed(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("type") type: String = "for_you" // for_you, following, trending
    ): Response<ApiResponse<PaginatedResponse<Reel>>>
    
    @GET("reels/{id}")
    suspend fun getReelById(
        @Header("Authorization") token: String,
        @Path("id") reelId: Int
    ): Response<ApiResponse<Reel>>
    
    @POST("reels")
    suspend fun uploadReel(
        @Header("Authorization") token: String,
        @Body request: UploadReelRequest
    ): Response<ApiResponse<Reel>>
    
    @PUT("reels/{id}")
    suspend fun updateReel(
        @Header("Authorization") token: String,
        @Path("id") reelId: Int,
        @Body request: UpdateReelRequest
    ): Response<ApiResponse<Reel>>
    
    @DELETE("reels/{id}")
    suspend fun deleteReel(
        @Header("Authorization") token: String,
        @Path("id") reelId: Int
    ): Response<ApiResponse<Any>>
    
    @POST("reels/{id}/like")
    suspend fun likeReel(
        @Header("Authorization") token: String,
        @Path("id") reelId: Int
    ): Response<ApiResponse<Any>>
    
    @DELETE("reels/{id}/like")
    suspend fun unlikeReel(
        @Header("Authorization") token: String,
        @Path("id") reelId: Int
    ): Response<ApiResponse<Any>>
    
    @POST("reels/{id}/view")
    suspend fun recordView(
        @Header("Authorization") token: String,
        @Path("id") reelId: Int,
        @Body request: ViewRequest
    ): Response<ApiResponse<Any>>
    
    @POST("reels/{id}/share")
    suspend fun shareReel(
        @Header("Authorization") token: String,
        @Path("id") reelId: Int,
        @Body request: ShareRequest
    ): Response<ApiResponse<Any>>
    
    @GET("reels/{id}/comments")
    suspend fun getReelComments(
        @Header("Authorization") token: String,
        @Path("id") reelId: Int,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<ApiResponse<PaginatedResponse<ReelComment>>>
    
    @POST("reels/{id}/comments")
    suspend fun addComment(
        @Header("Authorization") token: String,
        @Path("id") reelId: Int,
        @Body request: AddCommentRequest
    ): Response<ApiResponse<ReelComment>>
    
    @DELETE("comments/{id}")
    suspend fun deleteComment(
        @Header("Authorization") token: String,
        @Path("id") commentId: Int
    ): Response<ApiResponse<Any>>
    
    @POST("comments/{id}/like")
    suspend fun likeComment(
        @Header("Authorization") token: String,
        @Path("id") commentId: Int
    ): Response<ApiResponse<Any>>
    
    @GET("reels/trending")
    suspend fun getTrendingReels(
        @Header("Authorization") token: String,
        @Query("timeframe") timeframe: String = "24h", // 24h, 7d, 30d
        @Query("limit") limit: Int = 20
    ): Response<ApiResponse<List<Reel>>>
    
    @GET("reels/user/{userId}")
    suspend fun getUserReels(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<ApiResponse<PaginatedResponse<Reel>>>
    
    @GET("hashtags/{hashtag}/reels")
    suspend fun getReelsByHashtag(
        @Header("Authorization") token: String,
        @Path("hashtag") hashtag: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<ApiResponse<PaginatedResponse<Reel>>>
}

// Additional data classes for reels
data class UploadReelRequest(
    val videoUrl: String,
    val thumbnailUrl: String,
    val title: String,
    val description: String,
    val hashtags: List<String> = emptyList(),
    val duration: Int
)

data class UpdateReelRequest(
    val title: String? = null,
    val description: String? = null,
    val hashtags: List<String>? = null
)

data class ViewRequest(
    val duration: Int, // seconds watched
    val completed: Boolean = false
)

data class ShareRequest(
    val platform: String, // internal, whatsapp, instagram, etc.
    val userId: Int? = null // for internal shares
)

data class AddCommentRequest(
    val content: String,
    val parentCommentId: Int? = null // for replies
)