package com.temporarysocial.app.data.network

import com.temporarysocial.app.data.api.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkManager {
    
    // Base URLs for all 6 API groups
    companion object {
        private const val AUTH_BASE_URL = "https://x8ki-letl-twmt.n7.xano.io/api:v1AVHKjA/"
        private const val REALTIME_BASE_URL = "https://x8ki-letl-twmt.n7.xano.io/api:v1AVHKjA/ably/"
        private const val MESSAGING_BASE_URL = "https://x8ki-letl-twmt.n7.xano.io/api:0DFWZIuZ/"
        private const val PAYMENT_BASE_URL = "https://x8ki-letl-twmt.n7.xano.io/api:OdOlZRQi/"
        private const val REELS_BASE_URL = "https://x8ki-letl-twmt.n7.xano.io/api:Yb9nMimD/"
        private const val SOCIAL_BASE_URL = "https://x8ki-letl-twmt.n7.xano.io/api:mARu56Sc/"
        
        // Real-time connection ID from user
        private const val REALTIME_CONNECTION_ID = "iwwn9cR-ICaeaX8-p2JcU-CRKrg"
    }
    
    // Create OkHttpClient with common configurations
    private fun createOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        val headerInterceptor = Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("X-Realtime-Connection", REALTIME_CONNECTION_ID)
            
            chain.proceed(requestBuilder.build())
        }
        
        return OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    
    // Create Retrofit instance with specific base URL
    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    // API Service instances
    val authApiService: AuthApiService by lazy {
        createRetrofit(AUTH_BASE_URL).create(AuthApiService::class.java)
    }
    
    val realtimeApiService: RealtimeApiService by lazy {
        createRetrofit(REALTIME_BASE_URL).create(RealtimeApiService::class.java)
    }
    
    val messagingApiService: MessagingApiService by lazy {
        createRetrofit(MESSAGING_BASE_URL).create(MessagingApiService::class.java)
    }
    
    val paymentApiService: PaymentApiService by lazy {
        createRetrofit(PAYMENT_BASE_URL).create(PaymentApiService::class.java)
    }
    
    val reelsApiService: ReelsApiService by lazy {
        createRetrofit(REELS_BASE_URL).create(ReelsApiService::class.java)
    }
    
    val socialApiService: SocialApiService by lazy {
        createRetrofit(SOCIAL_BASE_URL).create(SocialApiService::class.java)
    }
}

// Extension function to handle API responses
suspend fun <T> safeApiCall(
    apiCall: suspend () -> retrofit2.Response<T>
): NetworkResult<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let {
                NetworkResult.Success(it)
            } ?: NetworkResult.Error("Empty response body")
        } else {
            NetworkResult.Error("API Error: ${response.code()} - ${response.message()}")
        }
    } catch (e: Exception) {
        NetworkResult.Error("Network Error: ${e.message}")
    }
}

// Sealed class for handling network results
sealed class NetworkResult<T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error<T>(val message: String) : NetworkResult<T>()
    data class Loading<T>(val isLoading: Boolean = true) : NetworkResult<T>()
}
package com.temporarysocial.app.data.network

import com.temporarysocial.app.data.api.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkManager {
    
    companion object {
        private const val BASE_URL = "https://api.temporarysocial.com/" // Replace with actual URL
    }
    
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
    
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    val authApiService: AuthApiService = retrofit.create(AuthApiService::class.java)
    val realtimeApiService: RealtimeApiService = retrofit.create(RealtimeApiService::class.java)
    val messagingApiService: MessagingApiService = retrofit.create(MessagingApiService::class.java)
    val paymentApiService: PaymentApiService = retrofit.create(PaymentApiService::class.java)
    val reelsApiService: ReelsApiService = retrofit.create(ReelsApiService::class.java)
    val socialApiService: SocialApiService = retrofit.create(SocialApiService::class.java)
}
