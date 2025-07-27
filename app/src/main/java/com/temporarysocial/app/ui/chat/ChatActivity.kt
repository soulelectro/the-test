package com.temporarysocial.app.ui.chat

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.temporarysocial.app.TemporarySocialApplication
import com.temporarysocial.app.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityChatBinding
    private lateinit var app: TemporarySocialApplication
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        app = application as TemporarySocialApplication
        
        setupUI()
    }
    
    private fun setupUI() {
        binding.apply {
            toolbar.setNavigationOnClickListener { finish() }
            
            tvPlaceholder.text = """
                💬 Ephemeral Chat
                
                🔗 Connected to: ${app.networkManager.messagingApiService.javaClass.simpleName}
                📡 Real-time connection: iwwn9cR-ICaeaX8-p2JcU-CRKrg
                ⏰ All messages expire in 5 hours
                
                Features:
                • Send/receive text messages
                • Mark messages as read
                • Chat rooms with multiple users
                • Automatic message expiry
                • Real-time message delivery
                • Unread message counts
                
                API Endpoints Used:
                • GET /messages - Fetch messages
                • POST /messages - Send message
                • GET /chat-rooms - Get chat rooms
                • POST /chat-rooms - Create chat room
                • PUT /messages/{id}/read - Mark as read
            """.trimIndent()
        }
    }
}
package com.temporarysocial.app.ui.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.temporarysocial.app.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityChatBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
    }
    
    private fun setupUI() {
        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }
            
            // TODO: Implement chat functionality
        }
    }
}
