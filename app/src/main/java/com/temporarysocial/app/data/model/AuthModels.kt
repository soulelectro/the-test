
package com.temporarysocial.app.data.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("phone_number")
    val phoneNumber: String
)

data class LoginResponse(
    @SerializedName("otp_sent")
    val otpSent: Boolean,
    
    @SerializedName("session_id")
    val sessionId: String,
    
    @SerializedName("message")
    val message: String
)

data class OTPVerificationRequest(
    @SerializedName("phone_number")
    val phoneNumber: String,
    
    @SerializedName("otp")
    val otp: String,
    
    @SerializedName("session_id")
    val sessionId: String
)

data class AuthResponse(
    @SerializedName("token")
    val token: String,
    
    @SerializedName("refresh_token")
    val refreshToken: String,
    
    @SerializedName("user")
    val user: User,
    
    @SerializedName("expires_in")
    val expiresIn: Long = 18000 // 5 hours in seconds
)

data class ApiResponse<T>(
    @SerializedName("success")
    val success: Boolean,
    
    @SerializedName("message")
    val message: String,
    
    @SerializedName("data")
    val data: T?,
    
    @SerializedName("error")
    val error: String?,
    
    @SerializedName("code")
    val code: Int = 200
)
