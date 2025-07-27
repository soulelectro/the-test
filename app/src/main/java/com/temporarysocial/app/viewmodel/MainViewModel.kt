package com.temporarysocial.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.temporarysocial.app.TemporarySocialApplication
import com.temporarysocial.app.data.model.*
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
            
            when (val result = safeApiCall { networkManager.authApiService.getProfile(token) }) {
                is NetworkResult.Success -> {
                    result.data.data?.let { user ->
                        _userProfile.value = user
                        _apiTestResult.value = "Profile loaded successfully"
                    }
                }
                is NetworkResult.Error -> {
                    _errorMessage.value = result.message
                    _apiTestResult.value = "Profile load failed: ${result.message}"
                }
                is NetworkResult.Loading -> {
                    // Handle loading state if needed
                }
            }
            
            _isLoading.value = false
        }
    }
    
    // Test API Group 1: Authentication & User Management
    fun testAuthAPI() {
        viewModelScope.launch {
            _isLoading.value = true
            _apiTestResult.value = "Testing Auth API..."
            
            val token = sessionManager.getAuthToken()
            if (token == null) {
                _apiTestResult.value = "Auth API Test Failed: No token"
                _isLoading.value = false
                return@launch
            }
            
            try {
                // Test 1: Get Profile
                val profileResult = safeApiCall { networkManager.authApiService.getProfile(token) }
                
                // Test 2: Get Session Status
                val sessionResult = safeApiCall { networkManager.authApiService.getSessionStatus(token) }
                
                // Test 3: Search Users
                val searchResult = safeApiCall { 
                    networkManager.authApiService.searchUsers(
                        token, 
                        SearchRequest("test", "users", 5)
                    ) 
                }
                
                val results = StringBuilder()
                results.append("🔐 AUTH API TESTS:\n")
                results.append("✅ Profile: ${if (profileResult is NetworkResult.Success) "SUCCESS" else "FAILED"}\n")
                results.append("✅ Session: ${if (sessionResult is NetworkResult.Success) "SUCCESS" else "FAILED"}\n")
                results.append("✅ Search: ${if (searchResult is NetworkResult.Success) "SUCCESS" else "FAILED"}\n")
                
                if (profileResult is NetworkResult.Success) {
                    profileResult.data.data?.let { user ->
                        results.append("User: ${user.username} (ID: ${user.id})\n")
                    }
                }
                
                _apiTestResult.value = results.toString()
                
            } catch (e: Exception) {
                _apiTestResult.value = "Auth API Test Error: ${e.message}"
            }
            
            _isLoading.value = false
        }
    }
    
    // Test API Group 2: Real-time Features
    fun testRealtimeAPI() {
        viewModelScope.launch {
            _isLoading.value = true
            _apiTestResult.value = "Testing Real-time API..."
            
            val token = sessionManager.getAuthToken()
            if (token == null) {
                _apiTestResult.value = "Realtime API Test Failed: No token"
                _isLoading.value = false
                return@launch
            }
            
            try {
                // Test 1: Get Ably Token
                val tokenResult = safeApiCall { networkManager.realtimeApiService.getAblyToken(token) }
                
                // Test 2: Get Active Channels
                val channelsResult = safeApiCall { networkManager.realtimeApiService.getActiveChannels(token) }
                
                val results = StringBuilder()
                results.append("⚡ REALTIME API TESTS:\n")
                results.append("✅ Ably Token: ${if (tokenResult is NetworkResult.Success) "SUCCESS" else "FAILED"}\n")
                results.append("✅ Channels: ${if (channelsResult is NetworkResult.Success) "SUCCESS" else "FAILED"}\n")
                
                if (tokenResult is NetworkResult.Success) {
                    tokenResult.data.data?.let { ablyToken ->
                        results.append("Connection ID: iwwn9cR-ICaeaX8-p2JcU-CRKrg\n")
                        results.append("Token expires: ${ablyToken.expires}\n")
                    }
                }
                
                _apiTestResult.value = results.toString()
                
            } catch (e: Exception) {
                _apiTestResult.value = "Realtime API Test Error: ${e.message}"
            }
            
            _isLoading.value = false
        }
    }
    
    // Test API Group 3: Ephemeral Messaging
    fun testMessagingAPI() {
        viewModelScope.launch {
            _isLoading.value = true
            _apiTestResult.value = "Testing Messaging API..."
            
            val token = sessionManager.getAuthToken()
            if (token == null) {
                _apiTestResult.value = "Messaging API Test Failed: No token"
                _isLoading.value = false
                return@launch
            }
            
            try {
                // Test 1: Get Messages
                val messagesResult = safeApiCall { networkManager.messagingApiService.getMessages(token) }
                
                // Test 2: Get Chat Rooms
                val chatRoomsResult = safeApiCall { networkManager.messagingApiService.getChatRooms(token) }
                
                // Test 3: Get Unread Count
                val unreadResult = safeApiCall { networkManager.messagingApiService.getUnreadMessageCount(token) }
                
                val results = StringBuilder()
                results.append("💬 MESSAGING API TESTS:\n")
                results.append("✅ Messages: ${if (messagesResult is NetworkResult.Success) "SUCCESS" else "FAILED"}\n")
                results.append("✅ Chat Rooms: ${if (chatRoomsResult is NetworkResult.Success) "SUCCESS" else "FAILED"}\n")
                results.append("✅ Unread Count: ${if (unreadResult is NetworkResult.Success) "SUCCESS" else "FAILED"}\n")
                results.append("⏰ All messages expire in 5 hours\n")
                
                _apiTestResult.value = results.toString()
                
            } catch (e: Exception) {
                _apiTestResult.value = "Messaging API Test Error: ${e.message}"
            }
            
            _isLoading.value = false
        }
    }
    
    // Test API Group 4: UPI Payments
    fun testPaymentAPI() {
        viewModelScope.launch {
            _isLoading.value = true
            _apiTestResult.value = "Testing Payment API..."
            
            val token = sessionManager.getAuthToken()
            if (token == null) {
                _apiTestResult.value = "Payment API Test Failed: No token"
                _isLoading.value = false
                return@launch
            }
            
            try {
                // Test 1: Get Payment History
                val historyResult = safeApiCall { networkManager.paymentApiService.getPaymentHistory(token) }
                
                // Test 2: Get Wallet Balance
                val balanceResult = safeApiCall { networkManager.paymentApiService.getWalletBalance(token) }
                
                // Test 3: Get Transaction History
                val transactionsResult = safeApiCall { networkManager.paymentApiService.getTransactionHistory(token) }
                
                val results = StringBuilder()
                results.append("💳 PAYMENT API TESTS:\n")
                results.append("✅ Payment History: ${if (historyResult is NetworkResult.Success) "SUCCESS" else "FAILED"}\n")
                results.append("✅ Wallet Balance: ${if (balanceResult is NetworkResult.Success) "SUCCESS" else "FAILED"}\n")
                results.append("✅ Transactions: ${if (transactionsResult is NetworkResult.Success) "SUCCESS" else "FAILED"}\n")
                results.append("💰 UPI payments via Razorpay\n")
                results.append("⏰ Payment requests expire in 5 hours\n")
                
                _apiTestResult.value = results.toString()
                
            } catch (e: Exception) {
                _apiTestResult.value = "Payment API Test Error: ${e.message}"
            }
            
            _isLoading.value = false
        }
    }
    
    // Test API Group 5: Reels/Video Feed
    fun testReelsAPI() {
        viewModelScope.launch {
            _isLoading.value = true
            _apiTestResult.value = "Testing Reels API..."
            
            val token = sessionManager.getAuthToken()
            if (token == null) {
                _apiTestResult.value = "Reels API Test Failed: No token"
                _isLoading.value = false
                return@launch
            }
            
            try {
                // Test 1: Get Feed
                val feedResult = safeApiCall { 
                    networkManager.reelsApiService.getFeed(token, type = "for_you") 
                }
                
                // Test 2: Get Trending Reels
                val trendingResult = safeApiCall { 
                    networkManager.reelsApiService.getTrendingReels(token, "24h") 
                }
                
                val results = StringBuilder()
                results.append("📱 REELS API TESTS:\n")
                results.append("✅ Feed: ${if (feedResult is NetworkResult.Success) "SUCCESS" else "FAILED"}\n")
                results.append("✅ Trending: ${if (trendingResult is NetworkResult.Success) "SUCCESS" else "FAILED"}\n")
                results.append("🎥 Video content like Instagram/YouTube\n")
                results.append("⏰ Reels expire in 5 hours\n")
                
                if (feedResult is NetworkResult.Success) {
                    feedResult.data.data?.let { feed ->
                        results.append("Found ${feed.data.size} reels in feed\n")
                    }
                }
                
                _apiTestResult.value = results.toString()
                
            } catch (e: Exception) {
                _apiTestResult.value = "Reels API Test Error: ${e.message}"
            }
            
            _isLoading.value = false
        }
    }
    
    // Test API Group 6: Social Features
    fun testSocialAPI() {
        viewModelScope.launch {
            _isLoading.value = true
            _apiTestResult.value = "Testing Social API..."
            
            val token = sessionManager.getAuthToken()
            if (token == null) {
                _apiTestResult.value = "Social API Test Failed: No token"
                _isLoading.value = false
                return@launch
            }
            
            try {
                // Test 1: Search Users
                val searchResult = safeApiCall { 
                    networkManager.socialApiService.searchUsers(
                        token, 
                        SearchRequest("friend", "users", 10)
                    ) 
                }
                
                // Test 2: Get Suggested Users
                val suggestionsResult = safeApiCall { 
                    networkManager.socialApiService.getSuggestedUsers(token, 10) 
                }
                
                // Test 3: Get Friends
                val friendsResult = safeApiCall { 
                    networkManager.socialApiService.getFriends(token) 
                }
                
                // Test 4: Get Activity Feed
                val activityResult = safeApiCall { 
                    networkManager.socialApiService.getActivityFeed(token) 
                }
                
                val results = StringBuilder()
                results.append("👥 SOCIAL API TESTS:\n")
                results.append("✅ Search: ${if (searchResult is NetworkResult.Success) "SUCCESS" else "FAILED"}\n")
                results.append("✅ Suggestions: ${if (suggestionsResult is NetworkResult.Success) "SUCCESS" else "FAILED"}\n")
                results.append("✅ Friends: ${if (friendsResult is NetworkResult.Success) "SUCCESS" else "FAILED"}\n")
                results.append("✅ Activity: ${if (activityResult is NetworkResult.Success) "SUCCESS" else "FAILED"}\n")
                results.append("🤝 Temporary following & friendships\n")
                results.append("⏰ Friend requests expire in 5 hours\n")
                
                _apiTestResult.value = results.toString()
                
            } catch (e: Exception) {
                _apiTestResult.value = "Social API Test Error: ${e.message}"
            }
            
            _isLoading.value = false
        }
    }
}