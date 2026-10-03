# Pravesh Software Solution — Android App

Ready-to-open Android Studio project for a service + UPI payment app.

## Services
- SEO Services
- Website Development
- Landing Page Development
- Software Testing

## Payment
Default configurable UPI VPA: `9871073225@upi`
Mobile number shown in the app: `9871073225`

**Important:** A mobile number is not necessarily the same as a UPI VPA. Before publishing the APK, verify that `9871073225@upi` is the actual VPA that receives your payments. If your UPI app shows a different VPA, edit `upiId` in `MainActivity.kt` and regenerate the QR.

The QR included is a valid static UPI QR without a fixed amount. The user enters the amount in the app; the Pay with UPI button passes the amount to compatible UPI apps. The QR flow asks the payer to enter the amount after scanning.

The app does **not** automatically verify payments. Customers should send their transaction screenshot/reference number through WhatsApp after paying.

## Build
1. Open this folder in Android Studio.
2. Let Gradle sync and install Android SDK 35 if requested.
3. Run on a device/emulator.
4. Build > Generate App Bundles or APKs > Generate APKs.
5. Debug APK will normally be under `app/build/outputs/apk/debug/`.

Package: `com.pravesh.software`
App name: `Pravesh Software Solution`
Minimum Android: 6.0 (API 23)
