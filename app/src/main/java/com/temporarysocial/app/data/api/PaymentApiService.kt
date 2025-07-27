package com.temporarysocial.app.data.api

import com.temporarysocial.app.data.model.*
import retrofit2.Response
import retrofit2.http.*

/**
 * API Group 4: UPI Payments (Razorpay Integration)
 * Base URL: https://x8ki-letl-twmt.n7.xano.io/api:OdOlZRQi
 */
interface PaymentApiService {
    
    @POST("payments/create-order")
    suspend fun createPaymentOrder(
        @Header("Authorization") token: String,
        @Body request: PaymentRequest
    ): Response<ApiResponse<RazorpayOrder>>
    
    @POST("payments/verify")
    suspend fun verifyPayment(
        @Header("Authorization") token: String,
        @Body request: PaymentVerificationRequest
    ): Response<ApiResponse<Payment>>
    
    @GET("payments")
    suspend fun getPaymentHistory(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("status") status: String? = null
    ): Response<ApiResponse<PaginatedResponse<Payment>>>
    
    @GET("payments/{id}")
    suspend fun getPaymentDetails(
        @Header("Authorization") token: String,
        @Path("id") paymentId: Int
    ): Response<ApiResponse<Payment>>
    
    @POST("payments/refund")
    suspend fun initiateRefund(
        @Header("Authorization") token: String,
        @Body request: RefundRequest
    ): Response<ApiResponse<RefundResponse>>
    
    @GET("payments/balance")
    suspend fun getWalletBalance(
        @Header("Authorization") token: String
    ): Response<ApiResponse<WalletBalanceResponse>>
    
    @POST("payments/withdraw")
    suspend fun withdrawFunds(
        @Header("Authorization") token: String,
        @Body request: WithdrawRequest
    ): Response<ApiResponse<WithdrawResponse>>
    
    @GET("payments/transactions")
    suspend fun getTransactionHistory(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("type") type: String? = null // sent, received, refund
    ): Response<ApiResponse<PaginatedResponse<Payment>>>
    
    @POST("payments/request")
    suspend fun requestPayment(
        @Header("Authorization") token: String,
        @Body request: PaymentRequestModel
    ): Response<ApiResponse<PaymentRequestModel>>
    
    @POST("payments/cancel")
    suspend fun cancelPayment(
        @Header("Authorization") token: String,
        @Body request: CancelPaymentRequest
    ): Response<ApiResponse<Any>>
    
    @GET("payments/expired")
    suspend fun getExpiredPayments(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<ApiResponse<PaginatedResponse<Payment>>>
}

// Additional data classes for payments
data class PaymentVerificationRequest(
    val razorpayPaymentId: String,
    val razorpayOrderId: String,
    val razorpaySignature: String,
    val paymentId: Int
)

data class RefundRequest(
    val paymentId: Int,
    val amount: Double? = null, // null for full refund
    val reason: String
)

data class RefundResponse(
    val refundId: String,
    val amount: Double,
    val status: String,
    val estimatedSettlement: String
)

data class WalletBalanceResponse(
    val balance: Double,
    val currency: String,
    val pendingAmount: Double,
    val lastUpdated: String
)

data class WithdrawRequest(
    val amount: Double,
    val bankAccount: BankAccountDetails
)

data class BankAccountDetails(
    val accountNumber: String,
    val ifscCode: String,
    val accountHolderName: String,
    val bankName: String
)

data class WithdrawResponse(
    val withdrawalId: String,
    val amount: Double,
    val status: String,
    val estimatedSettlement: String
)

data class PaymentRequestModel(
    val fromUserId: Int,
    val toUserId: Int,
    val amount: Double,
    val description: String,
    val expiresIn: Long = 18000000 // 5 hours in milliseconds
)

data class CancelPaymentRequest(
    val paymentId: Int,
    val reason: String
)