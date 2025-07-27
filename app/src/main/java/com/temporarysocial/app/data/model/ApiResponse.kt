package com.temporarysocial.app.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("success")
    val success: Boolean = false,
    
    @SerializedName("message")
    val message: String = "",
    
    @SerializedName("data")
    val data: T? = null,
    
    @SerializedName("error")
    val error: String = "",
    
    @SerializedName("code")
    val code: Int = 0
)

data class LoginRequest(
    @SerializedName("phone_number")
    val phoneNumber: String
)

data class LoginResponse(
    @SerializedName("otp_sent")
    val otpSent: Boolean = false,
    
    @SerializedName("session_id")
    val sessionId: String = "",
    
    @SerializedName("expires_in")
    val expiresIn: Long = 0 // 5 hours in milliseconds
)

data class OTPVerificationRequest(
    @SerializedName("phone_number")
    val phoneNumber: String,
    
    @SerializedName("otp")
    val otp: String,
    
    @SerializedName("session_id")
    val sessionId: String = ""
)

data class OTPVerificationResponse(
    @SerializedName("token")
    val token: String = "",
    
    @SerializedName("user")
    val user: User? = null,
    
    @SerializedName("expires_in")
    val expiresIn: Long = 0 // 5 hours in milliseconds
)

data class SearchRequest(
    @SerializedName("query")
    val query: String,
    
    @SerializedName("type")
    val type: String = "users", // users, hashtags, etc.
    
    @SerializedName("limit")
    val limit: Int = 20
)

data class FollowRequest(
    @SerializedName("user_id")
    val userId: Int
)

data class MessageRequest(
    @SerializedName("receiver_id")
    val receiverId: Int,
    
    @SerializedName("content")
    val content: String,
    
    @SerializedName("message_type")
    val messageType: String = "text"
)

data class PaginatedResponse<T>(
    @SerializedName("data")
    val data: List<T> = emptyList(),
    
    @SerializedName("total")
    val total: Int = 0,
    
    @SerializedName("page")
    val page: Int = 1,
    
    @SerializedName("limit")
    val limit: Int = 20,
    
    @SerializedName("has_more")
    val hasMore: Boolean = false
)