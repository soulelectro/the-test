
# 📱 Temporary Social - APK Releases

## 🚀 Latest Release

Download the latest APK from the [Releases](../../releases) section.

## 📥 Installation Instructions

1. **Download APK**: Click on the latest release and download `app-debug.apk`
2. **Enable Unknown Sources**: 
   - Go to Settings → Security → Unknown Sources
   - Enable "Install from Unknown Sources"
3. **Install APK**: Tap the downloaded APK file and follow installation prompts
4. **Launch App**: Open "Temporary Social" from your app drawer

## 🎯 App Features

### 🔐 Authentication
- **OTP Login**: Secure phone number verification
- **5-Hour Sessions**: Automatic logout after 5 hours
- **JWT Tokens**: Encrypted session management

### 💬 Ephemeral Messaging
- **Temporary Chats**: Messages expire in 5 hours
- **Real-time Delivery**: WebSocket-powered messaging
- **Read Receipts**: Message status tracking
- **Chat Rooms**: Group conversations

### 📱 Video Reels
- **Instagram-like Feed**: Vertical video scrolling
- **Like & Comment**: Social interaction features
- **Trending Content**: Popular reels discovery
- **5-Hour Expiry**: Content automatically expires

### 💳 UPI Payments
- **Razorpay Integration**: Secure payment processing
- **UPI Support**: Direct bank transfers
- **Payment History**: Transaction tracking
- **Temporary Requests**: Payment links expire in 5 hours

### 👥 Social Features
- **Friend Search**: Find and connect with users
- **Temporary Following**: Connections expire in 5 hours
- **Activity Feed**: Social interaction updates
- **Profile Management**: User profile customization

### ⚡ Real-time Features
- **WebSocket Connection**: Live message delivery
- **Presence Tracking**: Online/offline status
- **Notification System**: Real-time alerts
- **Session Countdown**: Live session timer

## 🔧 Technical Details

- **Platform**: Android 7.0+ (API 24+)
- **Language**: Kotlin
- **Architecture**: MVVM with Retrofit
- **APIs**: 6 Xano API groups integrated
- **Real-time**: WebSocket with connection ID `iwwn9cR-ICaeaX8-p2JcU-CRKrg`

## 🌐 API Endpoints

1. **Auth API**: User authentication and profiles
2. **Realtime API**: WebSocket connections and presence
3. **Messaging API**: Chat and messaging features
4. **Payment API**: UPI payments and transactions
5. **Reels API**: Video content and interactions
6. **Social API**: Friends, following, and social features

## 🔒 Security Features

- **Encrypted Storage**: JWT tokens stored securely
- **Session Management**: Automatic expiry and cleanup
- **Network Security**: HTTPS-only communication
- **Token Refresh**: Automatic session renewal

## 📱 System Requirements

- **Android Version**: 7.0 (Nougat) or higher
- **RAM**: Minimum 2GB recommended
- **Storage**: 50MB free space
- **Network**: Internet connection required
- **Permissions**: Phone, Storage, Camera (for reels)

## 🚀 Getting Started

1. **Install the APK** following instructions above
2. **Open the app** and enter your phone number
3. **Verify OTP** sent to your phone
4. **Start exploring** - you have 5 hours!
5. **Create content** - share reels, send messages, make payments
6. **Connect socially** - find friends and build temporary connections

## 🔄 Auto-Updates

This app uses GitHub Actions for automatic builds:
- **Commits to main/master**: Trigger new APK builds
- **Automatic Releases**: APKs uploaded to GitHub Releases
- **Version Tracking**: Each release gets a unique version number

## 🤝 Contributing

To contribute to this project:
1. Fork the repository
2. Make your changes
3. Test the build with `./build-for-github.sh`
4. Submit a pull request

## 📞 Support

If you encounter issues:
1. Check that your Android version is 7.0+
2. Ensure "Unknown Sources" is enabled
3. Try restarting your device after installation
4. Clear app data if login issues occur

---

**Built with ❤️ using Kotlin and modern Android development practices**

*Experience ephemeral social networking where everything matters for just 5 hours!*
