
#!/bin/bash

echo "🚀 Building Temporary Social APK for GitHub..."
echo "=============================================="

# Set JAVA_HOME
export JAVA_HOME=$(find /nix/store -name "*openjdk*" -type d | grep -E "openjdk-17\.[0-9]+" | head -1)
export PATH=$JAVA_HOME/bin:$PATH

# Build the APK
echo "📦 Building APK..."
./gradlew clean assembleDebug

# Check if build was successful
if [ -f "app/build/outputs/apk/debug/app-debug.apk" ]; then
    echo "✅ APK built successfully!"
    echo "📱 APK location: app/build/outputs/apk/debug/app-debug.apk"
    echo "📊 APK size: $(du -h app/build/outputs/apk/debug/app-debug.apk | cut -f1)"
    
    # Create a releases directory
    mkdir -p releases
    
    # Copy APK with timestamp
    TIMESTAMP=$(date +"%Y%m%d_%H%M%S")
    cp app/build/outputs/apk/debug/app-debug.apk "releases/temporary-social-${TIMESTAMP}.apk"
    
    echo "📁 APK copied to: releases/temporary-social-${TIMESTAMP}.apk"
    echo ""
    echo "🔗 To add to GitHub:"
    echo "1. Commit and push this repository to GitHub"
    echo "2. The GitHub Actions workflow will automatically build and create releases"
    echo "3. Or manually upload the APK from the releases/ directory"
    echo ""
    echo "📱 App Features:"
    echo "• 🔐 OTP Authentication (5-hour sessions)"
    echo "• 💬 Ephemeral Messaging"
    echo "• 📱 Video Reels"
    echo "• 💳 UPI Payments"
    echo "• 👥 Social Features"
    echo "• ⚡ Real-time chat"
else
    echo "❌ Build failed! Check the error messages above."
    exit 1
fi
