package com.temporarysocial.app.ui.profile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.temporarysocial.app.TemporarySocialApplication
import com.temporarysocial.app.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityProfileBinding
    private lateinit var app: TemporarySocialApplication
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        app = application as TemporarySocialApplication
        
        setupUI()
    }
    
    private fun setupUI() {
        binding.apply {
            toolbar.setNavigationOnClickListener { finish() }
            
            tvPlaceholder.text = """
                👤 User Profile
                
                🔗 Connected to: ${app.networkManager.authApiService.javaClass.simpleName}
                📱 Session: ${app.sessionManager.getRemainingSessionTimeFormatted()}
                ⏰ Profile data expires with session
                
                Features:
                • View user profile
                • Update profile information
                • Manage followers/following
                • Search for users
                • Session management
                • JWT token handling
                
                API Endpoints Used:
                • GET /profile - Get user profile
                • PUT /profile - Update profile
                • GET /users/{id} - Get user by ID
                • POST /search - Search users
                • GET /followers - Get followers
                • GET /following - Get following
                • POST /follow - Follow user
                • POST /unfollow - Unfollow user
            """.trimIndent()
        }
    }
}