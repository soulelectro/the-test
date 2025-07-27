package com.temporarysocial.app.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Message(
    @SerializedName("id")
    val id: Int = 0,
    
    @SerializedName("sender_id")
    val senderId: Int = 0,
    
    @SerializedName("receiver_id")
    val receiverId: Int = 0,
    
    @SerializedName("content")
    val content: String = "",
    
    @SerializedName("message_type")
    val messageType: String = "text", // text, image, video, payment
    
    @SerializedName("media_url")
    val mediaUrl: String = "",
    
    @SerializedName("is_read")
    val isRead: Boolean = false,
    
    @SerializedName("expires_at")
    val expiresAt: String = "",
    
    @SerializedName("created_at")
    val createdAt: String = "",
    
    @SerializedName("sender")
    val sender: User? = null,
    
    @SerializedName("receiver")
    val receiver: User? = null
) : Parcelable

@Parcelize
data class ChatRoom(
    @SerializedName("id")
    val id: Int = 0,
    
    @SerializedName("participant_1")
    val participant1: User? = null,
    
    @SerializedName("participant_2")
    val participant2: User? = null,
    
    @SerializedName("last_message")
    val lastMessage: Message? = null,
    
    @SerializedName("unread_count")
    val unreadCount: Int = 0,
    
    @SerializedName("expires_at")
    val expiresAt: String = "",
    
    @SerializedName("created_at")
    val createdAt: String = ""
) : Parcelable