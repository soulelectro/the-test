package com.temporarysocial.app.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @SerializedName("id")
    val id: Int = 0,
    
    @SerializedName("phone_number")
    val phoneNumber: String = "",
    
    @SerializedName("username")
    val username: String = "",
    
    @SerializedName("profile_picture")
    val profilePicture: String = "",
    
    @SerializedName("bio")
    val bio: String = "",
    
    @SerializedName("followers_count")
    val followersCount: Int = 0,
    
    @SerializedName("following_count")
    val followingCount: Int = 0,
    
    @SerializedName("is_verified")
    val isVerified: Boolean = false,
    
    @SerializedName("is_online")
    val isOnline: Boolean = false,
    
    @SerializedName("last_seen")
    val lastSeen: String = "",
    
    @SerializedName("created_at")
    val createdAt: String = "",
    
    @SerializedName("session_expires_at")
    val sessionExpiresAt: String = ""
) : Parcelable
package com.temporarysocial.app.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @SerializedName("id")
    val id: Int = 0,
    
    @SerializedName("username")
    val username: String = "",
    
    @SerializedName("phone_number")
    val phoneNumber: String = "",
    
    @SerializedName("profile_picture")
    val profilePicture: String = "",
    
    @SerializedName("bio")
    val bio: String = "",
    
    @SerializedName("is_verified")
    val isVerified: Boolean = false,
    
    @SerializedName("followers_count")
    val followersCount: Int = 0,
    
    @SerializedName("following_count")
    val followingCount: Int = 0,
    
    @SerializedName("posts_count")
    val postsCount: Int = 0,
    
    @SerializedName("wallet_balance")
    val walletBalance: Double = 0.0,
    
    @SerializedName("created_at")
    val createdAt: String = ""
) : Parcelable
