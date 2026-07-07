package com.mo.assistant.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mo.assistant.data.model.Message
import com.mo.assistant.data.model.Role
import com.mo.assistant.ui.theme.MOBgDark
import com.mo.assistant.ui.theme.MODanger
import com.mo.assistant.ui.theme.MOPurple
import com.mo.assistant.ui.theme.MOPurpleDeep
import com.mo.assistant.ui.theme.MOSurface
import com.mo.assistant.ui.theme.MOSurface2
import com.mo.assistant.ui.theme.MOTextPrimary
import com.mo.assistant.ui.theme.MOTextTertiary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MessageBubble(message: Message) {
    val isUser = message.role == Role.USER

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        if (!isUser) {
            // AI avatar
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(
                        Brush.linearGradient(listOf(MOPurple, MOPurpleDeep)),
                        RoundedCornerShape(50)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("M", fontSize = 12.sp, fontWeight = FontWeight.Black, color = MOBgDark)
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            horizontalAlignment = if (isUser) Alignment.End else Alignment.Start,
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = when {
                            message.isError -> MODanger.copy(alpha = 0.15f)
                            isUser -> MOPurpleDeep
                            else -> MOSurface
                        },
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = if (isUser) 16.dp else 4.dp,
                            bottomEnd = if (isUser) 4.dp else 16.dp
                        )
                    )
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                Text(
                    text = message.content,
                    color = if (message.isError) MODanger else MOTextPrimary,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = formatTime(message.timestamp),
                    color = MOTextTertiary,
                    fontSize = 10.sp
                )
                if (message.aiProvider != null && !isUser) {
                    Text("· ${message.aiProvider.emoji} ${message.aiProvider.displayName}", color = MOTextTertiary, fontSize = 10.sp)
                }
            }
        }
    }
}

private fun formatTime(ts: Long): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Date(ts))
}