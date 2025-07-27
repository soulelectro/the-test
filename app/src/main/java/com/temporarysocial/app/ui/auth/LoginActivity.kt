package com.temporarysocial.app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.temporarysocial.app.TemporarySocialApplication
import com.temporarysocial.app.databinding.ActivityLoginBinding
import com.temporarysocial.app.ui.MainActivity
import com.temporarysocial.app.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var app: TemporarySocialApplication
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        app = application as TemporarySocialApplication
        
        // Check if already logged in
        if (app.sessionManager.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }
        
        setupUI()
        setupObservers()
    }
    
    private fun setupUI() {
        binding.apply {
            btnSendOtp.setOnClickListener {
                val phoneNumber = etPhoneNumber.text.toString().trim()
                if (phoneNumber.isNotEmpty()) {
                    viewModel.sendOTP(phoneNumber)
                } else {
                    Toast.makeText(this@LoginActivity, "Please enter phone number", Toast.LENGTH_SHORT).show()
                }
            }
            
            btnVerifyOtp.setOnClickListener {
                val phoneNumber = etPhoneNumber.text.toString().trim()
                val otp = etOtp.text.toString().trim()
                if (phoneNumber.isNotEmpty() && otp.isNotEmpty()) {
                    viewModel.verifyOTP(phoneNumber, otp)
                } else {
                    Toast.makeText(this@LoginActivity, "Please enter phone number and OTP", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    private fun setupObservers() {
        viewModel.apply {
            otpSent.observe(this@LoginActivity) { sent ->
                if (sent) {
                    binding.layoutOtp.visibility = android.view.View.VISIBLE
                    binding.btnSendOtp.isEnabled = false
                    binding.btnVerifyOtp.isEnabled = true
                    Toast.makeText(this@LoginActivity, "OTP sent successfully!", Toast.LENGTH_SHORT).show()
                }
            }
            
            loginSuccess.observe(this@LoginActivity) { success ->
                if (success) {
                    Toast.makeText(this@LoginActivity, "Login successful!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
            }
            
            isLoading.observe(this@LoginActivity) { loading ->
                binding.progressBar.visibility = if (loading) 
                    android.view.View.VISIBLE else android.view.View.GONE
                binding.btnSendOtp.isEnabled = !loading
                binding.btnVerifyOtp.isEnabled = !loading && viewModel.otpSent.value == true
            }
            
            errorMessage.observe(this@LoginActivity) { error ->
                error?.let {
                    Toast.makeText(this@LoginActivity, it, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}