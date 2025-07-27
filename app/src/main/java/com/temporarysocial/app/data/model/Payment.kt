package com.temporarysocial.app.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Payment(
    @SerializedName("id")
    val id: Int = 0,
    
    @SerializedName("sender_id")
    val senderId: Int = 0,
    
    @SerializedName("receiver_id")
    val receiverId: Int = 0,
    
    @SerializedName("amount")
    val amount: Double = 0.0,
    
    @SerializedName("currency")
    val currency: String = "INR",
    
    @SerializedName("payment_method")
    val paymentMethod: String = "UPI",
    
    @SerializedName("razorpay_payment_id")
    val razorpayPaymentId: String = "",
    
    @SerializedName("razorpay_order_id")
    val razorpayOrderId: String = "",
    
    @SerializedName("status")
    val status: String = "pending", // pending, completed, failed, expired
    
    @SerializedName("description")
    val description: String = "",
    
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
data class PaymentRequest(
    @SerializedName("receiver_id")
    val receiverId: Int,
    
    @SerializedName("amount")
    val amount: Double,
    
    @SerializedName("description")
    val description: String = ""
) : Parcelable

@Parcelize
data class RazorpayOrder(
    @SerializedName("id")
    val id: String = "",
    
    @SerializedName("amount")
    val amount: Int = 0,
    
    @SerializedName("currency")
    val currency: String = "INR",
    
    @SerializedName("status")
    val status: String = ""
) : Parcelable