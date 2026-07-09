package com.mo.assistant.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.material3.Text
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mo.assistant.ui.theme.MOBgDark
import com.mo.assistant.ui.theme.MOPurple
import com.mo.assistant.ui.theme.MOPurpleDeep
import com.mo.assistant.ui.theme.MOSurface
import com.mo.assistant.ui.theme.MOTextTertiary

@Composable
fun TypingIndicator() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .background(
                    Brush.linearGradient(listOf(MOPurple, MOPurpleDeep)),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text("M", fontSize = 12.sp, fontWeight = FontWeight.Black, color = MOBgDark)
        }
        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .background(MOSurface, CircleShape)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                repeat(3) { i ->
                    val transition = rememberInfiniteTransition(label = "dot$i")
                    val alpha by transition.animateFloat(
                        initialValue = 0.3f,
                        targetValue = 1f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(durationMillis = 600, delayMillis = i * 150),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "alpha$i"
                    )
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(MOTextTertiary.copy(alpha = alpha), CircleShape)
                    )
                }
            }
        }
    }
}
