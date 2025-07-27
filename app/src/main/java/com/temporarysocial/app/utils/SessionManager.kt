package com.temporarysocial.app.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import kotlinx.coroutines.*
import java.util.*

class SessionManager(private val context: Context) {
    
    companion object {
        private const val PREF_NAME = "temporary_social_session"
        private const val KEY_TOKEN = "jwt_token"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_SESSION_START = "session_start"
        private const val KEY_SESSION_EXPIRES = "session_expires"
        private const val SESSION_DURATION = 5 * 60 * 60 * 1000L // 5 hours in milliseconds
    }
    
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    
    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        PREF_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    
    private var sessionMonitoringJob: Job? = null
    private var sessionExpiryCallback: (() -> Unit)? = null
    
    // Save user session
    fun saveSession(token: String, userId: Int) {
        val currentTime = System.currentTimeMillis()
        val expiryTime = currentTime + SESSION_DURATION
        
        sharedPreferences.edit().apply {
            putString(KEY_TOKEN, token)
            putInt(KEY_USER_ID, userId)
            putLong(KEY_SESSION_START, currentTime)
            putLong(KEY_SESSION_EXPIRES, expiryTime)
            apply()
        }
        
        startSessionMonitoring()
    }
    
    // Get JWT token with Bearer prefix
    fun getAuthToken(): String? {
        val token = sharedPreferences.getString(KEY_TOKEN, null)
        return if (token != null) "Bearer $token" else null
    }
    
    // Get raw JWT token
    fun getRawToken(): String? {
        return sharedPreferences.getString(KEY_TOKEN, null)
    }
    
    // Get current user ID
    fun getUserId(): Int {
        return sharedPreferences.getInt(KEY_USER_ID, -1)
    }
    
    // Check if session is valid
    fun isSessionValid(): Boolean {
        val expiryTime = sharedPreferences.getLong(KEY_SESSION_EXPIRES, 0)
        return System.currentTimeMillis() < expiryTime
    }
    
    // Get remaining session time in milliseconds
    fun getRemainingSessionTime(): Long {
        val expiryTime = sharedPreferences.getLong(KEY_SESSION_EXPIRES, 0)
        val remainingTime = expiryTime - System.currentTimeMillis()
        return if (remainingTime > 0) remainingTime else 0
    }
    
    // Get remaining session time in human readable format
    fun getRemainingSessionTimeFormatted(): String {
        val remainingTime = getRemainingSessionTime()
        if (remainingTime <= 0) return "Expired"
        
        val hours = remainingTime / (60 * 60 * 1000)
        val minutes = (remainingTime % (60 * 60 * 1000)) / (60 * 1000)
        val seconds = (remainingTime % (60 * 1000)) / 1000
        
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
    
    // Clear session
    fun clearSession() {
        sharedPreferences.edit().clear().apply()
        stopSessionMonitoring()
    }
    
    // Extend session (if backend supports it)
    fun extendSession() {
        val currentTime = System.currentTimeMillis()
        val newExpiryTime = currentTime + SESSION_DURATION
        
        sharedPreferences.edit().apply {
            putLong(KEY_SESSION_EXPIRES, newExpiryTime)
            apply()
        }
    }
    
    // Set callback for session expiry
    fun setSessionExpiryCallback(callback: () -> Unit) {
        sessionExpiryCallback = callback
    }
    
    // Start monitoring session expiry
    fun startSessionMonitoring() {
        stopSessionMonitoring()
        
        sessionMonitoringJob = CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                if (!isSessionValid()) {
                    sessionExpiryCallback?.invoke()
                    break
                }
                delay(1000) // Check every second
            }
        }
    }
    
    // Stop session monitoring
    private fun stopSessionMonitoring() {
        sessionMonitoringJob?.cancel()
        sessionMonitoringJob = null
    }
    
    // Get session start time
    fun getSessionStartTime(): Long {
        return sharedPreferences.getLong(KEY_SESSION_START, 0)
    }
    
    // Get session expiry time
    fun getSessionExpiryTime(): Long {
        return sharedPreferences.getLong(KEY_SESSION_EXPIRES, 0)
    }
    
    // Check if user is logged in
    fun isLoggedIn(): Boolean {
        return getRawToken() != null && getUserId() != -1 && isSessionValid()
    }
    
    // Get session progress (0.0 to 1.0)
    fun getSessionProgress(): Float {
        val startTime = getSessionStartTime()
        val expiryTime = getSessionExpiryTime()
        val currentTime = System.currentTimeMillis()
        
        if (startTime == 0L || expiryTime == 0L) return 0f
        
        val totalDuration = expiryTime - startTime
        val elapsed = currentTime - startTime
        
        return (elapsed.toFloat() / totalDuration).coerceIn(0f, 1f)
    }
    
    // Session warning thresholds
    fun isSessionNearExpiry(warningMinutes: Int = 30): Boolean {
        val remainingTime = getRemainingSessionTime()
        return remainingTime <= (warningMinutes * 60 * 1000)
    }
    
    fun isSessionCritical(criticalMinutes: Int = 5): Boolean {
        val remainingTime = getRemainingSessionTime()
        return remainingTime <= (criticalMinutes * 60 * 1000)
    }
}