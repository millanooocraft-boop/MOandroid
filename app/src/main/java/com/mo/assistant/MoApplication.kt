package com.mo.assistant

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.mo.assistant.ads.AdManager

/**
 * MO Application — Initializes AdMob and global services.
 */
class MoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize Google Mobile Ads SDK
        MobileAds.initialize(this) { initializationStatus ->
            android.util.Log.d("MO", "AdMob initialized: $initializationStatus")
        }

        // Preload ads
        AdManager.preloadAds(this)
    }
}