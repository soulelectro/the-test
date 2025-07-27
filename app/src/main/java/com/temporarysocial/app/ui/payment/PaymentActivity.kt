package com.temporarysocial.app.ui.payment

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.temporarysocial.app.TemporarySocialApplication
import com.temporarysocial.app.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityPaymentBinding
    private lateinit var app: TemporarySocialApplication
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        app = application as TemporarySocialApplication
        
        setupUI()
    }
    
    private fun setupUI() {
        binding.apply {
            toolbar.setNavigationOnClickListener { finish() }
            
            tvPlaceholder.text = """
                💳 UPI Payments
                
                🔗 Connected to: ${app.networkManager.paymentApiService.javaClass.simpleName}
                💰 Razorpay Integration
                ⏰ Payment requests expire in 5 hours
                
                Features:
                • Create payment orders
                • UPI payment processing
                • Payment verification
                • Wallet balance management
                • Transaction history
                • Refund processing
                • Payment requests
                
                API Endpoints Used:
                • POST /payments/create-order - Create order
                • POST /payments/verify - Verify payment
                • GET /payments - Payment history
                • GET /payments/balance - Wallet balance
                • POST /payments/refund - Initiate refund
                • GET /payments/transactions - Transaction history
            """.trimIndent()
        }
    }
}