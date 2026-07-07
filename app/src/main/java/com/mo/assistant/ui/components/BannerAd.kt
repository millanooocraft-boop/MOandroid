package com.mo.assistant.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mo.assistant.BuildConfig

/**
 * Banner ad placeholder.
 *
 * TODO: Replace with actual AdView when ready:
 *
 * AndroidView(
 *     factory = { context ->
 *         AdView(context).apply {
 *             setAdSize(AdSize.BANNER)
 *             adUnitId = BuildConfig.ADMOB_BANNER_ID
 *             loadAd(AdRequest.Builder().build())
 *         }
 *     },
 *     modifier = Modifier.fillMaxWidth()
 * )
 */
@Composable
fun BannerAd() {
    if (BuildConfig.ADMOB_BANNER_ID.startsWith("ca-app-pub-XXXX")) return  // not configured

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color(0xFF1F2540), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "📢 إعلان · Banner Ad",
            color = Color(0xFF94A3B8),
            fontSize = 11.sp
        )
    }
}