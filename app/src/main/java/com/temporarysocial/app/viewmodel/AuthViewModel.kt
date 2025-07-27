package com.temporarysocial.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.temporarysocial.app.TemporarySocialApplication
import com.temporarysocial.app.data.model.LoginRequest
import com.temporarysocial.app.data.model.OTPVerificationRequest
import com.temporarysocial.app.data.network.NetworkResult
import com.temporarysocial.app.data.network.safeApiCall
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    
    private val app = application as TemporarySocialApplication
    private val networkManager = app.networkManager
    private val sessionManager = app.sessionManager
    
    private val _otpSent = MutableLiveData<Boolean>()
    val otpSent: LiveData<Boolean> = _otpSent
    
    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage
    
    private var currentSessionId: String = ""
    
    fun sendOTP(phoneNumber: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            val request = LoginRequest(phoneNumber)
            
            when (val result = safeApiCall { networkManager.authApiService.login(request) }) {
                is NetworkResult.Success -> {
                    result.data.data?.let { loginResponse ->
                        if (loginResponse.otpSent) {
                            currentSessionId = loginResponse.sessionId
                            _otpSent.value = true
                        } else {
                            _errorMessage.value = "Failed to send OTP"
                        }
                    } ?: run {
                        _errorMessage.value = "Invalid response from server"
                    }
                }
                is NetworkResult.Error -> {
                    _errorMessage.value = result.message
                }
                is NetworkResult.Loading -> {
                    // Handle loading state if needed
                }
            }
            
            _isLoading.value = false
        }
    }
    
    fun verifyOTP(phoneNumber: String, otp: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            val request = OTPVerificationRequest(phoneNumber, otp, currentSessionId)
            
            when (val result = safeApiCall { networkManager.authApiService.verifyOTP(request) }) {
                is NetworkResult.Success -> {
                    result.data.data?.let { otpResponse ->
                        if (otpResponse.token.isNotEmpty() && otpResponse.user != null) {
                            // Save session
                            sessionManager.saveSession(otpResponse.token, otpResponse.user.id)
                            _loginSuccess.value = true
                        } else {
                            _errorMessage.value = "Invalid OTP or user data"
                        }
                    } ?: run {
                        _errorMessage.value = "Invalid response from server"
                    }
                }
                is NetworkResult.Error -> {
                    _errorMessage.value = result.message
                }
                is NetworkResult.Loading -> {
                    // Handle loading state if needed
                }
            }
            
            _isLoading.value = false
        }
    }
}