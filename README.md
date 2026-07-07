# MO — Android AI Assistant 🤖

> مساعدك الذكي الشخصي — Android native app built with Jetpack Compose

## ✨ Features (Phase 1 - MVP)

- ✅ شات كامل مع **Google Gemini API**
- ✅ **AI Cascade** — fallback تلقائي بين 5 مزودي AI
- ✅ واجهة **Compose** حديثة مع Dark Theme
- ✅ دعم **RTL عربي** كامل + English
- ✅ Voice service skeleton (Phase 2)
- ✅ AdMob integration skeleton

---

## 🚀 Quick Start

### 1. Prerequisites

- **Android Studio** Hedgehog (2023.1.1) أو أحدث
- **JDK 17**
- **Android SDK** 34
- **Gemini API Key** (مجاني من [aistudio.google.com/app/apikey](https://aistudio.google.com/app/apikey))

### 2. خطوات التشغيل

```bash
# 1. افتح المجلد في Android Studio
# File > Open > اختار مجلد mo-android

# 2. Android Studio هيحمّل Gradle تلقائياً
# لما يطلب منك — وافق

# 3. المفتاح موجود في local.properties:
GEMINI_API_KEY=AQ.Ab8RN6Iuc9WNE4oX3UZg79LXBk_NiBWTUrOJyLXaIyuvo6rGvw

# ⚠️ مهم: بعد ما تاخذ المشروع، روح aistudio.google.com
# والغي هذا المفتاح + اعمل واحد جديد. المفتاح اللي في الكود
# صار مكشوف في الشات.
```

### 3. شغّل على جوالك

```
Run > Run 'app' > اختار جهازك
```

---

## 📁 Project Structure

```
mo-android/
├── app/
│   ├── build.gradle.kts            ← Dependencies + BuildConfig
│   ├── src/main/
│   │   ├── AndroidManifest.xml
│   │   ├── java/com/mo/assistant/
│   │   │   ├── MoApplication.kt          ← App init + AdMob
│   │   │   ├── MainActivity.kt           ← Entry point
│   │   │   ├── data/
│   │   │   │   ├── api/
│   │   │   │   │   ├── GeminiService.kt  ← Retrofit interface
│   │   │   │   │   └── GeminiModels.kt   ← Request/Response DTOs
│   │   │   │   ├── model/
│   │   │   │   │   └── Message.kt        ← Domain models + AIProvider enum
│   │   │   │   └── repository/
│   │   │   │       └── ChatRepository.kt ← API calls + cascade logic
│   │   │   ├── ui/
│   │   │   │   ├── MoApp.kt              ← Navigation
│   │   │   │   ├── theme/                ← Color, Theme, Typography
│   │   │   │   ├── screens/
│   │   │   │   │   ├── HomeScreen.kt     ← Landing with animated orb
│   │   │   │   │   ├── ChatScreen.kt     ← Main chat UI
│   │   │   │   │   └── SettingsScreen.kt ← Settings + integrations
│   │   │   │   ├── components/
│   │   │   │   │   ├── MessageBubble.kt
│   │   │   │   │   ├── TypingIndicator.kt
│   │   │   │   │   └── BannerAd.kt
│   │   │   │   └── viewmodel/
│   │   │   │       └── ChatViewModel.kt  ← StateFlow + business logic
│   │   │   ├── ads/
│   │   │   │   └── AdManager.kt          ← AdMob wrapper
│   │   │   └── voice/
│   │   │       └── VoiceForegroundService.kt ← Phase 2 stub
│   │   └── res/                          ← Resources (colors, strings, drawables)
│   └── proguard-rules.pro
├── build.gradle.kts                  ← Top-level Gradle
├── settings.gradle.kts
├── gradle.properties
├── local.properties                  ← 🔐 Secrets (NOT in git)
└── .gitignore
```

---

## 🔑 Configuration

### Gemini API Key

```properties
# local.properties
GEMINI_API_KEY=YOUR_NEW_KEY_HERE
```

### AdMob (اختياري للـ development)

```properties
ADMOB_APP_ID=ca-app-pub-XXXX~XXXX
ADMOB_BANNER_ID=ca-app-pub-XXXX/XXXX
ADMOB_INTERSTITIAL_ID=ca-app-pub-XXXX/XXXX
ADMOB_REWARDED_ID=ca-app-pub-XXXX/XXXX
```

### كيف تاخذ Ad Unit IDs

1. روح [admob.google.com](https://admob.google.com)
2. اعمل app جديد
3. اعمل ad unit (Banner, Interstitial, Rewarded)
4. انسخ الـ IDs

---

## 🧠 AI Providers Cascade

الترتيب الحالي (تقدر تغيره في `ChatRepository.kt`):

```kotlin
cascadeOrder = listOf(
    AIProvider.GEMINI_FLASH,  // 1. مجاني، افتراضي
    AIProvider.MINIMAX,       // 2. متوازن
    AIProvider.CLAUDE,        // 3. ملك التحليل
    AIProvider.GPT4O,         // 4. الأقوى إبداعياً
    AIProvider.GEMINI_PRO     // 5. Fallback ذكي
)
```

لإضافة AI provider جديد:
1. أضف enum case في `AIProvider`
2. اعمل service خاص فيه (مثل `ClaudeService.kt`)
3. حدّث `ChatRepository` ليستخدمه

---

## 🎨 Theme

الألوان الأساسية:
- `MOPurple`: `#A78BFA`
- `MOAccentGreen`: `#34D399`
- `MOBgDark`: `#0A0E1A`

تقدر تعدّل من `Color.kt` و `Theme.kt`.

---

## 🛣️ Roadmap

| Phase | Weeks | Features |
|-------|-------|----------|
| ✅ Phase 1 | 1-3 | MVP: Chat + Gemini + UI |
| 🔜 Phase 2 | 4-6 | Wake word + Voice STT/TTS |
| 📋 Phase 3 | 7-10 | Integrations (YouTube, IG, X) |
| 📋 Phase 4 | 11-12 | Polish + Marketing |

---

## 🐛 Troubleshooting

### Gradle Sync Failed

```bash
# امسح cache وأعد البناء
./gradlew clean
./gradlew --refresh-dependencies
```

### Build Error: SDK not found

عدّل `local.properties`:
```
sdk.dir=/Users/YOUR_USERNAME/Library/Android/sdk
```

### Gemini API 403

تأكد إن:
- المفتاح صحيح في `local.properties`
- الـ API مفعل في [Google Cloud Console](https://console.cloud.google.com)
- اخترت "Generative Language API"

---

## ⚠️ Security Notes

1. **لا تشارك `local.properties` أبداً** — موجود في `.gitignore`
2. **غيّر مفتاح Gemini** اللي كان في الشات — اعمل rotate
3. للإنتاج: استخدم [EncryptedSharedPreferences](https://developer.android.com/reference/androidx/security/crypto/EncryptedSharedPreferences) لتخزين مفاتيح المستخدمين
4. للـ ProGuard: تأكد من قواعد الحماية في `proguard-rules.pro`

---

## 📜 License

Personal project — جميع الحقوق محفوظة.

---

## 🤝 Contributing

هذا مشروع شخصي، بس لو عندك أفكار:
1. Fork
2. اعمل feature branch
3. commit + push
4. اعمل Pull Request

---

**Built with ❤️ using Kotlin + Jetpack Compose**
**Powered by Google Gemini** 🌟