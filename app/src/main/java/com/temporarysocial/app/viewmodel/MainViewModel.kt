package com.temporarysocial.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.temporarysocial.app.TemporarySocialApplication
import com.temporarysocial.app.data.model.User
import com.temporarysocial.app.data.network.NetworkResult
import com.temporarysocial.app.data.network.safeApiCall
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val app = application as TemporarySocialApplication
    private val networkManager = app.networkManager
    private val sessionManager = app.sessionManager

    // LiveData for UI
    private val _userProfile = MutableLiveData<User?>()
    val userProfile: LiveData<User?> = _userProfile

    private val _sessionTime = MutableLiveData<String>()
    val sessionTime: LiveData<String> = _sessionTime

    private val _apiTestResult = MutableLiveData<String>()
    val apiTestResult: LiveData<String> = _apiTestResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    // Update session time
    fun updateSessionTime(timeFormatted: String) {
        _sessionTime.value = timeFormatted
    }

    // Load user profile
    fun loadUserProfile() {
        viewModelScope.launch {
            _isLoading.value = true

            val token = sessionManager.getAuthToken()
            if (token == null) {
                _errorMessage.value = "No authentication token found"
                _isLoading.value = false
                return@launch
            }

            try {
                when (val result = safeApiCall { networkManager.authApiService.getProfile(token) }) {
                    is NetworkResult.Success -> {
                        result.data.data?.let { user ->
                            _userProfile.value = user
                        }
                    }
                    is NetworkResult.Error -> {
                        _errorMessage.value = result.message
                    }
                    is NetworkResult.Exception -> {
                        _errorMessage.value = "Network error: ${result.e.message}"
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error loading profile: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun testAuthAPI() {
        viewModelScope.launch {
            _apiTestResult.value = "🔐 Testing Authentication APIs...\n"
            val token = sessionManager.getAuthToken() ?: return@launch

            try {
                // Test profile API
                when (val result = safeApiCall { networkManager.authApiService.getProfile(token) }) {
                    is NetworkResult.Success -> {
                        _apiTestResult.value += "✅ Profile API: Success\n"
                    }
                    is NetworkResult.Error -> {
                        _apiTestResult.value += "❌ Profile API: ${result.message}\n"
                    }
                    is NetworkResult.Exception -> {
                        _apiTestResult.value += "❌ Profile API: Network error\n"
                    }
                }
            } catch (e: Exception) {
                _apiTestResult.value += "❌ Auth API Test failed: ${e.message}\n"
            }
        }
    }

    fun testRealtimeAPI() {
        viewModelScope.launch {
            _apiTestResult.value = "⚡ Testing Realtime APIs...\n"
            val token = sessionManager.getAuthToken() ?: return@launch

            try {
                when (val result = safeApiCall { networkManager.realtimeApiService.getAblyToken(token) }) {
                    is NetworkResult.Success -> {
                        _apiTestResult.value += "✅ Ably Token: Success\n"
                    }
                    is NetworkResult.Error -> {
                        _apiTestResult.value += "❌ Ably Token: ${result.message}\n"
                    }
                    is NetworkResult.Exception -> {
                        _apiTestResult.value += "❌ Ably Token: Network error\n"
                    }
                }
            } catch (e: Exception) {
                _apiTestResult.value += "❌ Realtime API Test failed: ${e.message}\n"
            }
        }
    }

    fun testMessagingAPI() {
        viewModelScope.launch {
            _apiTestResult.value = "💬 Testing Messaging APIs...\n"
            val token = sessionManager.getAuthToken() ?: return@launch

            try {
                when (val result = safeApiCall { networkManager.messagingApiService.getMessages(token, 1) }) {
                    is NetworkResult.Success -> {
                        _apiTestResult.value += "✅ Messages API: Success\n"
                    }
                    is NetworkResult.Error -> {
                        _apiTestResult.value += "❌ Messages API: ${result.message}\n"
                    }
                    is NetworkResult.Exception -> {
                        _apiTestResult.value += "❌ Messages API: Network error\n"
                    }
                }
            } catch (e: Exception) {
                _apiTestResult.value += "❌ Messaging API Test failed: ${e.message}\n"
            }
        }
    }

    fun testPaymentAPI() {
        viewModelScope.launch {
            _apiTestResult.value = "💳 Testing Payment APIs...\n"
            val token = sessionManager.getAuthToken() ?: return@launch

            try {
                when (val result = safeApiCall { networkManager.paymentApiService.getPaymentHistory(token) }) {
                    is NetworkResult.Success -> {
                        _apiTestResult.value += "✅ Payment History: Success\n"
                    }
                    is NetworkResult.Error -> {
                        _apiTestResult.value += "❌ Payment History: ${result.message}\n"
                    }
                    is NetworkResult.Exception -> {
                        _apiTestResult.value += "❌ Payment History: Network error\n"
                    }
                }
            } catch (e: Exception) {
                _apiTestResult.value += "❌ Payment API Test failed: ${e.message}\n"
            }
        }
    }

    fun testReelsAPI() {
        viewModelScope.launch {
            _apiTestResult.value = "🎬 Testing Reels APIs...\n"
            val token = sessionManager.getAuthToken() ?: return@launch

            try {
                when (val result = safeApiCall { networkManager.reelsApiService.getReelsFeed(token) }) {
                    is NetworkResult.Success -> {
                        _apiTestResult.value += "✅ Reels Feed: Success\n"
                    }
                    is NetworkResult.Error -> {
                        _apiTestResult.value += "❌ Reels Feed: ${result.message}\n"
                    }
                    is NetworkResult.Exception -> {
                        _apiTestResult.value += "❌ Reels Feed: Network error\n"
                    }
                }
            } catch (e: Exception) {
                _apiTestResult.value += "❌ Reels API Test failed: ${e.message}\n"
            }
        }
    }

    fun testSocialAPI() {
        viewModelScope.launch {
            _apiTestResult.value = "👥 Testing Social APIs...\n"
            val token = sessionManager.getAuthToken() ?: return@launch

            try {
                when (val result = safeApiCall { networkManager.socialApiService.searchUsers(token, "test") }) {
                    is NetworkResult.Success -> {
                        _apiTestResult.value += "✅ User Search: Success\n"
                    }
                    is NetworkResult.Error -> {
                        _apiTestResult.value += "❌ User Search: ${result.message}\n"
                    }
                    is NetworkResult.Exception -> {
                        _apiTestResult.value += "❌ User Search: Network error\n"
                    }
                }
            } catch (e: Exception) {
                _apiTestResult.value += "❌ Social API Test failed: ${e.message}\n"
            }
        }
    }
}