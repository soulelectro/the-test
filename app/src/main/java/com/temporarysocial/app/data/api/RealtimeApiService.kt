package com.temporarysocial.app.data.api

import com.temporarysocial.app.data.model.*
import retrofit2.Response
import retrofit2.http.*

/**
 * API Group 2: Real-time Features (Ably Integration)
 * Base URL: https://x8ki-letl-twmt.n7.xano.io/api:v1AVHKjA/ably
 */
interface RealtimeApiService {
    
    @GET("token")
    suspend fun getAblyToken(@Header("Authorization") token: String): Response<ApiResponse<AblyTokenResponse>>
    
    @GET("channels")
    suspend fun getActiveChannels(@Header("Authorization") token: String): Response<ApiResponse<List<AblyChannel>>>
    
    @POST("channels/join")
    suspend fun joinChannel(
        @Header("Authorization") token: String,
        @Body request: JoinChannelRequest
    ): Response<ApiResponse<Any>>
    
    @POST("channels/leave")
    suspend fun leaveChannel(
        @Header("Authorization") token: String,
        @Body request: LeaveChannelRequest
    ): Response<ApiResponse<Any>>
    
    @GET("presence/{channelName}")
    suspend fun getChannelPresence(
        @Header("Authorization") token: String,
        @Path("channelName") channelName: String
    ): Response<ApiResponse<List<AblyPresence>>>
    
    @POST("publish")
    suspend fun publishMessage(
        @Header("Authorization") token: String,
        @Body request: PublishMessageRequest
    ): Response<ApiResponse<Any>>
    
    @GET("history/{channelName}")
    suspend fun getChannelHistory(
        @Header("Authorization") token: String,
        @Path("channelName") channelName: String,
        @Query("limit") limit: Int = 50
    ): Response<ApiResponse<List<AblyMessage>>>
}

// Additional data classes for Ably integration
data class AblyTokenResponse(
    val token: String,
    val expires: Long,
    val clientId: String
)

data class AblyChannel(
    val name: String,
    val isActive: Boolean,
    val memberCount: Int
)

data class JoinChannelRequest(
    val channelName: String,
    val clientData: Map<String, Any> = emptyMap()
)

data class LeaveChannelRequest(
    val channelName: String
)

data class AblyPresence(
    val clientId: String,
    val data: Map<String, Any>,
    val timestamp: Long
)

data class PublishMessageRequest(
    val channelName: String,
    val eventName: String,
    val data: Map<String, Any>
)

data class AblyMessage(
    val id: String,
    val name: String,
    val data: Map<String, Any>,
    val timestamp: Long,
    val clientId: String
)