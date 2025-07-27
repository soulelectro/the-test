package com.temporarysocial.app.ui.reels

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.temporarysocial.app.TemporarySocialApplication
import com.temporarysocial.app.databinding.ActivityReelsBinding

class ReelsActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityReelsBinding
    private lateinit var app: TemporarySocialApplication
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReelsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        app = application as TemporarySocialApplication
        
        setupUI()
    }
    
    private fun setupUI() {
        binding.apply {
            toolbar.setNavigationOnClickListener { finish() }
            
            tvPlaceholder.text = """
                📱 Video Reels
                
                🔗 Connected to: ${app.networkManager.reelsApiService.javaClass.simpleName}
                🎥 Instagram/YouTube-like feed
                ⏰ All reels expire in 5 hours
                
                Features:
                • Video feed (For You, Following, Trending)
                • Upload video reels
                • Like/unlike reels
                • Comment on reels
                • Share reels
                • View tracking
                • Hashtag support
                • User reels
                
                API Endpoints Used:
                • GET /feed - Get video feed
                • POST /reels - Upload reel
                • GET /reels/{id} - Get reel by ID
                • POST /reels/{id}/like - Like reel
                • GET /reels/{id}/comments - Get comments
                • POST /reels/{id}/comments - Add comment
                • GET /reels/trending - Trending reels
                • GET /reels/user/{userId} - User reels
            """.trimIndent()
        }
    }
}
package com.temporarysocial.app.ui.reels

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.temporarysocial.app.databinding.ActivityReelsBinding

class ReelsActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityReelsBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReelsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
    }
    
    private fun setupUI() {
        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }
            
            // TODO: Implement reels functionality
        }
    }
}
