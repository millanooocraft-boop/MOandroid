# Add project specific ProGuard rules here.
-keepattributes Signature, *Annotation*, EnclosingMethod
-keep class com.mo.assistant.data.model.** { *; }
-keep class com.google.gson.** { *; }
-keep class retrofit2.** { *; }
-dontwarn javax.annotation.**

# Keep BuildConfig
-keep class com.mo.assistant.BuildConfig { *; }