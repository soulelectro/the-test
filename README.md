# 🌟 Temporary Social - Ephemeral Social Networking App

A Kotlin Android app built with Retrofit that connects to **6 different Xano API groups** for an ephemeral social networking experience where everything expires in 5 hours.

## 📱 App Concept
**"Temporary Social – Ephemeral Social Networking App"**

A social networking app where users log in via OTP, all sessions/messages/payments expire in 5 hours, and real-time features like chat and UPI payments are integrated.

## 🔗 API Configuration

### 6 Xano API Groups Connected:
1. **Auth API**: `https://x8ki-letl-twmt.n7.xano.io/api:v1AVHKjA/`
2. **Realtime API**: `https://x8ki-letl-twmt.n7.xano.io/api:v1AVHKjA/ably/`
3. **Messaging API**: `https://x8ki-letl-twmt.n7.xano.io/api:0DFWZIuZ/`
4. **Payment API**: `https://x8ki-letl-twmt.n7.xano.io/api:OdOlZRQi/`
5. **Reels API**: `https://x8ki-letl-twmt.n7.xano.io/api:Yb9nMimD/`
6. **Social API**: `https://x8ki-letl-twmt.n7.xano.io/api:mARu56Sc/`

### 📡 Real-time Connection ID
```
iwwn9cR-ICaeaX8-p2JcU-CRKrg
```

## 🎯 Features Implemented

### 🔐 Authentication (API Group 1)
- **OTP-based login** using Twilio integration
- **JWT token management** with encrypted SharedPreferences
- **5-hour session countdown** with automatic expiry
- User profile management
- Session refresh and monitoring

### ⚡ Real-time Features (API Group 2)
- **Ably integration** for real-time messaging
- WebSocket connections with your provided connection ID
- Channel management and presence tracking
- Real-time message publishing and history

### 💬 Ephemeral Messaging (API Group 3)
- **5-hour message expiry** with TTL (Time To Live)
- Chat rooms and direct messaging
- Message read receipts and unread counts
- Automatic message cleanup
- Real-time message delivery

### 💳 UPI Payments (API Group 4)
- **Razorpay integration** for UPI payments
- Payment order creation and verification
- Wallet balance management
- Transaction history and refunds
- **5-hour payment request expiry**

### 📱 Video Reels (API Group 5)
- **Instagram/YouTube-like video feed**
- Video upload and playback
- Like, comment, and share functionality
- Trending reels and hashtag support
- **5-hour reel expiry**

### 👥 Social Features (API Group 6)
- Friend search and suggestions
- **Temporary following** and friendships
- Activity feed and mutual friends
- User blocking and friend requests
- **5-hour friend request expiry**

## 🏗️ Architecture

### MVVM Pattern
- **ViewModels** for business logic
- **LiveData** for reactive UI updates
- **Repository pattern** for data management
- **Coroutines** for asynchronous operations

### Key Components

#### 📦 Data Layer
```kotlin
// API Services (6 Retrofit interfaces)
- AuthApiService.kt
- RealtimeApiService.kt  
- MessagingApiService.kt
- PaymentApiService.kt
- ReelsApiService.kt
- SocialApiService.kt

// Data Models
- User.kt, Message.kt, Payment.kt, Reel.kt
- ApiResponse.kt (Generic response wrapper)

// Network Management
- NetworkManager.kt (All 6 Retrofit clients)
- NetworkResult.kt (Response handling)
```

#### 🎨 UI Layer
```kotlin
// Activities
- MainActivity.kt (API testing & navigation)
- LoginActivity.kt (OTP authentication)
- ChatActivity.kt, PaymentActivity.kt
- ProfileActivity.kt, ReelsActivity.kt

// ViewModels
- MainViewModel.kt (Demonstrates all 6 APIs)
- AuthViewModel.kt (OTP login flow)
```

#### 🔧 Utils
```kotlin
// Session Management
- SessionManager.kt (5-hour countdown, JWT tokens)
- TemporarySocialApplication.kt (App initialization)
```

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or newer
- Kotlin 1.9.10+
- Android API 24+ (Android 7.0)

### Dependencies
```kotlin
// Networking
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

// Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

// UI & Material Design
implementation("com.google.android.material:material:1.11.0")
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

// Security
implementation("androidx.security:security-crypto:1.1.0-alpha06")

// Payments
implementation("com.razorpay:checkout:1.6.33")

// WebSocket for real-time
implementation("org.java-websocket:Java-WebSocket:1.5.4")
```

### Installation
1. Clone the repository
2. Open in Android Studio
3. Sync project with Gradle files
4. Run the app on device/emulator

## 📱 Usage

### 1. Login Flow
- Enter phone number
- Receive OTP via Twilio
- Verify OTP to get JWT token
- Session starts with 5-hour countdown

### 2. Main Dashboard
- **Session Timer**: Shows remaining time (HH:MM:SS)
- **Progress Bar**: Visual session progress
- **API Test Buttons**: Test all 6 API groups
- **Navigation**: Access different app sections

### 3. API Testing
Click any API test button to see:
- ✅ Success/Failure status
- 📊 Response data
- 🔗 Endpoint information
- ⏰ Expiry details

### 4. Real-time Features
- Messages appear instantly
- Session countdown updates every second
- Automatic logout on session expiry

## 🔧 Configuration

### Network Headers
All API calls include:
```kotlin
Content-Type: application/json
Accept: application/json
X-Realtime-Connection: iwwn9cR-ICaeaX8-p2JcU-CRKrg
Authorization: Bearer <JWT_TOKEN>
```

### Session Management
```kotlin
// 5-hour session duration
private const val SESSION_DURATION = 5 * 60 * 60 * 1000L

// Encrypted token storage
EncryptedSharedPreferences with AES256_GCM

// Automatic session monitoring
Coroutine-based countdown timer
```

## 🎨 UI/UX Features

### Modern Material Design
- **Material Cards** for content sections
- **Outlined buttons** for navigation
- **Progress indicators** for session tracking
- **Color-coded warnings** (Green → Yellow → Red)

### Session Countdown
- **Real-time timer** showing HH:MM:SS
- **Progress bar** with color changes
- **Automatic warnings** at 30min and 5min
- **Forced logout** on expiry

### API Test Results
- **Terminal-style output** with monospace font
- **Emoji indicators** for different API groups
- **Success/failure status** for each endpoint
- **Real-time connection info**

## 🔐 Security Features

### JWT Token Management
- **Encrypted storage** using Android Security Crypto
- **Automatic token refresh** (if backend supports)
- **Secure session validation**
- **Token expiry handling**

### Network Security
- **Certificate pinning** ready
- **Request/response logging** (debug only)
- **Error handling** for network failures
- **Timeout configurations**

## 🚀 Backend Integration

### Expected Backend Stack
```
- Node.js + Express
- MongoDB with TTL indexes
- JWT Authentication  
- Socket.io for real-time
- Twilio for OTP
- Razorpay for payments
```

### API Response Format
```json
{
  "success": true,
  "message": "Operation successful",
  "data": { /* Response data */ },
  "error": null,
  "code": 200
}
```

## 📊 Testing

### API Test Coverage
Each API group includes comprehensive tests:
- ✅ **Authentication**: Login, profile, search
- ✅ **Real-time**: Ably tokens, channels
- ✅ **Messaging**: Messages, chat rooms, unread counts
- ✅ **Payments**: Orders, history, balance
- ✅ **Reels**: Feed, trending, comments
- ✅ **Social**: Search, friends, activity

### Error Handling
- Network connectivity issues
- API response validation
- Session expiry scenarios
- Token refresh failures

## 🔄 Future Enhancements

### Planned Features
- [ ] **Push notifications** for messages
- [ ] **Biometric authentication** option
- [ ] **Dark/Light theme** toggle
- [ ] **Offline message queue**
- [ ] **Video compression** for reels
- [ ] **Advanced payment features**

### Scalability
- [ ] **Database caching** with Room
- [ ] **Image loading** with Glide
- [ ] **Navigation Component**
- [ ] **Dependency Injection** with Hilt
- [ ] **Unit testing** with JUnit/Mockito

## 🤝 Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## 👨‍💻 Developer

Built with ❤️ using Kotlin and modern Android development practices.

### Key Technologies
- **Kotlin** - Primary language
- **Retrofit** - HTTP client
- **Coroutines** - Async programming
- **MVVM** - Architecture pattern
- **Material Design** - UI/UX
- **JWT** - Authentication
- **WebSocket** - Real-time features

---

## 🚀 Quick Start Commands

```bash
# Clone the repository
git clone <repository-url>

# Open in Android Studio
cd temporary-social-app
# File → Open → Select project folder

# Build and run
./gradlew assembleDebug
./gradlew installDebug
```

**Ready to experience ephemeral social networking! 🌟**