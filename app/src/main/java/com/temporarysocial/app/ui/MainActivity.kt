
package com.temporarysocial.app.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.temporarysocial.app.TemporarySocialApplication
import com.temporarysocial.app.databinding.ActivityMainBinding
import com.temporarysocial.app.ui.auth.LoginActivity
import com.temporarysocial.app.ui.chat.ChatActivity
import com.temporarysocial.app.ui.payment.PaymentActivity
import com.temporarysocial.app.ui.profile.ProfileActivity
import com.temporarysocial.app.ui.reels.ReelsActivity
import com.temporarysocial.app.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var app: TemporarySocialApplication
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        app = application as TemporarySocialApplication
        
        // Check if user is logged in
        if (!app.sessionManager.isLoggedIn()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }
        
        setupUI()
        setupObservers()
        setupSessionMonitoring()
        
        // Load initial data
        loadInitialData()
    }
    
    private fun setupUI() {
        binding.apply {
            // Navigation buttons
            btnProfile.setOnClickListener {
                startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
            }
            
            btnChat.setOnClickListener {
                startActivity(Intent(this@MainActivity, ChatActivity::class.java))
            }
            
            btnReels.setOnClickListener {
                startActivity(Intent(this@MainActivity, ReelsActivity::class.java))
            }
            
            btnPayments.setOnClickListener {
                startActivity(Intent(this@MainActivity, PaymentActivity::class.java))
            }
            
            // Test API buttons
            btnTestAuth.setOnClickListener {
                testAuthAPI()
            }
            
            btnTestRealtime.setOnClickListener {
                testRealtimeAPI()
            }
            
            btnTestMessaging.setOnClickListener {
                testMessagingAPI()
            }
            
            btnTestPayment.setOnClickListener {
                testPaymentAPI()
            }
            
            btnTestReels.setOnClickListener {
                testReelsAPI()
            }
            
            btnTestSocial.setOnClickListener {
                testSocialAPI()
            }
            
            btnLogout.setOnClickListener {
                logout()
            }
        }
    }
    
    private fun setupObservers() {
        viewModel.apply {
            userProfile.observe(this@MainActivity) { user ->
                user?.let {
                    binding.tvWelcome.text = "Welcome, ${it.username}!"
                    binding.tvUserId.text = "User ID: ${it.id}"
                }
            }
            
            sessionTime.observe(this@MainActivity) { timeFormatted ->
                binding.tvSessionTime.text = "Session expires in: $timeFormatted"
            }
            
            apiTestResult.observe(this@MainActivity) { result ->
                binding.tvApiResult.text = result
            }
            
            isLoading.observe(this@MainActivity) { loading ->
                binding.progressBar.visibility = if (loading) 
                    android.view.View.VISIBLE else android.view.View.GONE
            }
            
            errorMessage.observe(this@MainActivity) { error ->
                error?.let {
                    Toast.makeText(this@MainActivity, it, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    
    private fun setupSessionMonitoring() {
        app.sessionManager.setSessionExpiryCallback {
            runOnUiThread {
                Toast.makeText(this, "Session expired! Please login again.", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
        
        // Update session time every second
        lifecycleScope.launch {
            while (true) {
                try {
                    val timeFormatted = app.sessionManager.getRemainingSessionTimeFormatted()
                    viewModel.updateSessionTime(timeFormatted)
                    
                    // Update progress bar
                    val progress = app.sessionManager.getSessionProgress()
                    binding.sessionProgressBar.progress = (progress * 100).toInt()
                    
                    // Warning for session expiry
                    if (app.sessionManager.isSessionCritical()) {
                        binding.sessionProgressBar.progressTintList = 
                            android.content.res.ColorStateList.valueOf(android.graphics.Color.RED)
                    }
                    
                    kotlinx.coroutines.delay(1000)
                } catch (e: Exception) {
                    break
                }
            }
        }
    }
    
    private fun loadInitialData() {
        viewModel.loadUserProfile()
    }
    
    private fun testAuthAPI() {
        viewModel.testAuthAPI()
    }
    
    private fun testRealtimeAPI() {
        viewModel.testRealtimeAPI()
    }
    
    private fun testMessagingAPI() {
        viewModel.testMessagingAPI()
    }
    
    private fun testPaymentAPI() {
        viewModel.testPaymentAPI()
    }
    
    private fun testReelsAPI() {
        viewModel.testReelsAPI()
    }
    
    private fun testSocialAPI() {
        viewModel.testSocialAPI()
    }
    
    private fun logout() {
        app.sessionManager.clearSession()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
