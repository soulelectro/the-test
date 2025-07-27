package com.temporarysocial.app.data.api

import com.temporarysocial.app.data.model.*
import retrofit2.Response
import retrofit2.http.*

/**
 * API Group 1: Authentication & User Management
 * Base URL: https://x8ki-letl-twmt.n7.xano.io/api:v1AVHKjA
 */
interface AuthApiService {
    
    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<ApiResponse<LoginResponse>>
    
    @POST("verifyOTP")
    suspend fun verifyOTP(@Body request: OTPVerificationRequest): Response<ApiResponse<OTPVerificationResponse>>
    
    @POST("logout")
    suspend fun logout(@Header("Authorization") token: String): Response<ApiResponse<Any>>
    
    @GET("profile")
    suspend fun getProfile(@Header("Authorization") token: String): Response<ApiResponse<User>>
    
    @PUT("profile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body user: User
    ): Response<ApiResponse<User>>
    
    @GET("users/{id}")
    suspend fun getUserById(
        @Header("Authorization") token: String,
        @Path("id") userId: Int
    ): Response<ApiResponse<User>>
    
    @POST("search")
    suspend fun searchUsers(
        @Header("Authorization") token: String,
        @Body request: SearchRequest
    ): Response<ApiResponse<PaginatedResponse<User>>>
    
    @POST("follow")
    suspend fun followUser(
        @Header("Authorization") token: String,
        @Body request: FollowRequest
    ): Response<ApiResponse<Any>>
    
    @POST("unfollow")
    suspend fun unfollowUser(
        @Header("Authorization") token: String,
        @Body request: FollowRequest
    ): Response<ApiResponse<Any>>
    
    @GET("followers")
    suspend fun getFollowers(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<ApiResponse<PaginatedResponse<User>>>
    
    @GET("following")
    suspend fun getFollowing(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<ApiResponse<PaginatedResponse<User>>>
    
    @GET("session/status")
    suspend fun getSessionStatus(@Header("Authorization") token: String): Response<ApiResponse<Any>>
    
    @POST("session/refresh")
    suspend fun refreshSession(@Header("Authorization") token: String): Response<ApiResponse<OTPVerificationResponse>>
}