# SCDMonitor (Android)

This repository contains an Android app skeleton for SCDMonitor — a research-focused app to predict vaso-occlusive crises (VOC) in sickle cell disease.

Quick test & development guide

Prerequisites:
- JDK 11+
- Android SDK and platform 33
- Android Studio (recommended)
- Firebase project with `google-services.json` for the app
- A test Android device (for BLE/Health Connect)

Build and install (PowerShell):
```powershell
cd C:\Users\USER\Documents\capacitor-pos
.\android\gradlew.bat :app:assembleDebug
.\android\gradlew.bat :app:installDebug
```

Run unit tests:
```powershell
.\android\gradlew.bat :app:testDebugUnitTest
```

Run instrumentation tests (device connected):
```powershell
.\android\gradlew.bat :app:connectedAndroidTest
```

Health Connect and BLE:
- Install Health Connect on test device and grant permissions.
- Pair a BLE heart-rate/SpO2 device or use a simulator app (e.g., nRF Connect) to advertise test data.

Firebase Cloud Functions:
- A sample Cloud Function is provided in `functions/` that forwards Firestore `alerts` documents to FCM topic `clinicians`.
- Deploy with Firebase CLI after configuring the functions project.
# capacitor-pos

CapacitorPos Capacitor Plugin

## Install

```bash
npm install capacitor-pos
npx cap sync
```

## API

<docgen-index></docgen-index>

<docgen-api>
<!-- run docgen to generate docs from the source -->
<!-- More info: https://github.com/ionic-team/capacitor-docgen -->
</docgen-api>
