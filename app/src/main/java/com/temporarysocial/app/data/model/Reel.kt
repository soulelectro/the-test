package com.temporarysocial.app.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Reel(
    @SerializedName("id")
    val id: Int = 0,
    
    @SerializedName("user_id")
    val userId: Int = 0,
    
    @SerializedName("video_url")
    val videoUrl: String = "",
    
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String = "",
    
    @SerializedName("title")
    val title: String = "",
    
    @SerializedName("description")
    val description: String = "",
    
    @SerializedName("duration")
    val duration: Int = 0, // in seconds
    
    @SerializedName("likes_count")
    val likesCount: Int = 0,
    
    @SerializedName("comments_count")
    val commentsCount: Int = 0,
    
    @SerializedName("shares_count")
    val sharesCount: Int = 0,
    
    @SerializedName("views_count")
    val viewsCount: Int = 0,
    
    @SerializedName("is_liked")
    val isLiked: Boolean = false,
    
    @SerializedName("expires_at")
    val expiresAt: String = "",
    
    @SerializedName("created_at")
    val createdAt: String = "",
    
    @SerializedName("user")
    val user: User? = null,
    
    @SerializedName("hashtags")
    val hashtags: List<String> = emptyList()
) : Parcelable

@Parcelize
data class ReelComment(
    @SerializedName("id")
    val id: Int = 0,
    
    @SerializedName("reel_id")
    val reelId: Int = 0,
    
    @SerializedName("user_id")
    val userId: Int = 0,
    
    @SerializedName("content")
    val content: String = "",
    
    @SerializedName("likes_count")
    val likesCount: Int = 0,
    
    @SerializedName("is_liked")
    val isLiked: Boolean = false,
    
    @SerializedName("expires_at")
    val expiresAt: String = "",
    
    @SerializedName("created_at")
    val createdAt: String = "",
    
    @SerializedName("user")
    val user: User? = null
) : Parcelable