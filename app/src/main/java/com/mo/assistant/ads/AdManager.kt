package com.mo.assistant.ads

import android.content.Context

/**
 * AdManager — placeholder for AdMob integration.
 * Real implementation will load banner/interstitial/rewarded ads.
 */
object AdManager {

    fun preloadAds(context: Context) {
        // TODO: Initialize ad units
        // MobileAds.initialize(context) { }
        // InterstitialAd.load(...)
    }

    fun showInterstitial(activity: android.app.Activity) {
        // TODO: Show interstitial ad
    }

    fun showRewarded(activity: android.app.Activity, onReward: (Int) -> Unit) {
        // TODO: Show rewarded ad and grant reward
        onReward(50)  // placeholder: grant 50 free messages
    }
}