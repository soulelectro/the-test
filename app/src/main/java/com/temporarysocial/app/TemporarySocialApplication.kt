package com.temporarysocial.app

import android.app.Application
import com.temporarysocial.app.data.network.NetworkManager
import com.temporarysocial.app.utils.SessionManager

class TemporarySocialApplication : Application() {
    
    lateinit var networkManager: NetworkManager
        private set
        
    lateinit var sessionManager: SessionManager
        private set
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize network manager
        networkManager = NetworkManager()
        
        // Initialize session manager
        sessionManager = SessionManager(this)
        
        // Start session monitoring
        sessionManager.startSessionMonitoring()
    }
}