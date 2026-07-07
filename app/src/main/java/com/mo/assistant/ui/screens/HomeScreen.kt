package com.mo.assistant.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mo.assistant.ui.components.BannerAd
import com.mo.assistant.ui.theme.MOAccentGreen
import com.mo.assistant.ui.theme.MOBgDark
import com.mo.assistant.ui.theme.MOPurple
import com.mo.assistant.ui.theme.MOPurpleDeep
import com.mo.assistant.ui.theme.MOSurface
import com.mo.assistant.ui.theme.MOTextSecondary
import com.mo.assistant.ui.theme.MOTextTertiary

/**
 * Home screen — landing page with the animated MO orb.
 */
@Composable
fun HomeScreen(
    onNavigateToChat: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "orb")

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MOBgDark
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.statusBars.asPaddingValues())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "مساء الخير 👋",
                        color = MOTextTertiary,
                        fontSize = 13.sp
                    )
                    Text(
                        text = "يا صديقي، كيف أساعدك؟",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(MOPurpleDeep, CircleShape)
                        .clickable { onNavigateToSettings() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Box(
                modifier = Modifier.size(220.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .rotate(rotation)
                        .alpha(0.3f)
                        .background(
                            Brush.sweepGradient(
                                listOf(
                                    MOPurple.copy(alpha = 0f),
                                    MOPurple,
                                    MOAccentGreen,
                                    MOPurple.copy(alpha = 0f)
                                )
                            ),
                            CircleShape
                        )
                )
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .scale(pulse)
                        .background(
                            Brush.radialGradient(listOf(MOPurple, MOPurpleDeep)),
                            CircleShape
                        )
                        .clickable { onNavigateToChat() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "M",
                        fontSize = 64.sp,
                        fontWeight = FontWeight.Black,
                        color = MOBgDark
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "جاهز للاستماع",
                color = MOTextSecondary,
                fontSize = 14.sp
            )
            Text(
                text = "Gemini Flash · مجاني",
                color = MOAccentGreen,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickAction("💬", "شات", Modifier.weight(1f), onNavigateToChat)
                QuickAction("🎙️", "صوت", Modifier.weight(1f), onNavigateToChat)
                QuickAction("🔌", "تطبيقات", Modifier.weight(1f), onNavigateToSettings)
                QuickAction("⚙️", "إعدادات", Modifier.weight(1f), onNavigateToSettings)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                shape = RoundedCornerShape(50),
                color = MOSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateToChat() }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(
                                Brush.linearGradient(listOf(MOPurple, MOAccentGreen)),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🎤", fontSize = 16.sp)
                    }
                    Text(
                        text = "قول \"يا مو\" أو اكتب رسالتك...",
                        color = MOTextTertiary,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            BannerAd()

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun QuickAction(
    emoji: String,
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .aspectRatio(1f)
            .background(MOSurface, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(emoji, fontSize = 28.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            label,
            color = MOTextSecondary,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}