package com.temporarysocial.app.data.api

import com.temporarysocial.app.data.model.*
import retrofit2.Response
import retrofit2.http.*

/**
 * API Group 3: Ephemeral Messaging
 * Base URL: https://x8ki-letl-twmt.n7.xano.io/api:0DFWZIuZ
 */
interface MessagingApiService {
    
    @GET("messages")
    suspend fun getMessages(
        @Header("Authorization") token: String,
        @Query("chat_room_id") chatRoomId: Int? = null,
        @Query("user_id") userId: Int? = null,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 50
    ): Response<ApiResponse<PaginatedResponse<Message>>>
    
    @POST("messages")
    suspend fun sendMessage(
        @Header("Authorization") token: String,
        @Body request: MessageRequest
    ): Response<ApiResponse<Message>>
    
    @PUT("messages/{id}/read")
    suspend fun markMessageAsRead(
        @Header("Authorization") token: String,
        @Path("id") messageId: Int
    ): Response<ApiResponse<Any>>
    
    @DELETE("messages/{id}")
    suspend fun deleteMessage(
        @Header("Authorization") token: String,
        @Path("id") messageId: Int
    ): Response<ApiResponse<Any>>
    
    @GET("chat-rooms")
    suspend fun getChatRooms(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<ApiResponse<PaginatedResponse<ChatRoom>>>
    
    @POST("chat-rooms")
    suspend fun createChatRoom(
        @Header("Authorization") token: String,
        @Body request: CreateChatRoomRequest
    ): Response<ApiResponse<ChatRoom>>
    
    @GET("chat-rooms/{id}")
    suspend fun getChatRoom(
        @Header("Authorization") token: String,
        @Path("id") chatRoomId: Int
    ): Response<ApiResponse<ChatRoom>>
    
    @DELETE("chat-rooms/{id}")
    suspend fun deleteChatRoom(
        @Header("Authorization") token: String,
        @Path("id") chatRoomId: Int
    ): Response<ApiResponse<Any>>
    
    @GET("messages/unread-count")
    suspend fun getUnreadMessageCount(
        @Header("Authorization") token: String
    ): Response<ApiResponse<UnreadCountResponse>>
    
    @POST("messages/bulk-read")
    suspend fun markAllMessagesAsRead(
        @Header("Authorization") token: String,
        @Body request: BulkReadRequest
    ): Response<ApiResponse<Any>>
    
    @GET("messages/expired")
    suspend fun getExpiredMessages(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 50
    ): Response<ApiResponse<PaginatedResponse<Message>>>
    
    @POST("messages/extend-expiry")
    suspend fun extendMessageExpiry(
        @Header("Authorization") token: String,
        @Body request: ExtendExpiryRequest
    ): Response<ApiResponse<Any>>
}

// Additional data classes for messaging
data class CreateChatRoomRequest(
    val participantId: Int,
    val initialMessage: String? = null
)

data class UnreadCountResponse(
    val totalUnread: Int,
    val chatRooms: List<ChatRoomUnread>
)

data class ChatRoomUnread(
    val chatRoomId: Int,
    val unreadCount: Int,
    val lastMessage: Message?
)

data class BulkReadRequest(
    val chatRoomId: Int? = null,
    val messageIds: List<Int>? = null
)

data class ExtendExpiryRequest(
    val messageId: Int,
    val additionalHours: Int = 5
)