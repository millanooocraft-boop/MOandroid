package com.mo.assistant.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mo.assistant.data.model.AIProvider
import com.mo.assistant.ui.theme.MOAccentGreen
import com.mo.assistant.ui.theme.MOBgDark
import com.mo.assistant.ui.theme.MOPurple
import com.mo.assistant.ui.theme.MOSurface
import com.mo.assistant.ui.theme.MOSurface2
import com.mo.assistant.ui.theme.MOTextPrimary
import com.mo.assistant.ui.theme.MOTextSecondary
import com.mo.assistant.ui.theme.MOTextTertiary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    var cascadeEnabled by remember { mutableStateOf(true) }
    var voiceEnabled by remember { mutableStateOf(false) }
    var selectedProvider by remember { mutableStateOf(AIProvider.GEMINI_FLASH) }
    var language by remember { mutableStateOf("ar") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MOBgDark
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.statusBars.asPaddingValues())
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MOTextPrimary
                        )
                    }
                    Text(
                        "الإعدادات",
                        color = MOTextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }

            // AI Section
            item {
                SettingsSectionHeader("🧠 الذكاء الاصطناعي")
                SettingsCard {
                    SettingsToggle(
                        label = "Auto Cascade",
                        sublabel = "يختار AI الأنسب تلقائياً",
                        checked = cascadeEnabled,
                        onChange = { cascadeEnabled = it }
                    )
                    SettingsDivider()
                    SettingsClickable(
                        label = "AI الافتراضي",
                        value = selectedProvider.displayNameAr,
                        onClick = { /* show picker */ }
                    )
                    SettingsDivider()
                    SettingsClickable(
                        label = "اللغة",
                        value = if (language == "ar") "العربية" else "English",
                        onClick = { language = if (language == "ar") "en" else "ar" }
                    )
                }
            }

            // Voice Section
            item {
                SettingsSectionHeader("🎙️ الصوت")
                SettingsCard {
                    SettingsToggle(
                        label = "Wake Word \"يا مو\"",
                        sublabel = "تنشيط صوتي في الخلفية",
                        checked = voiceEnabled,
                        onChange = { voiceEnabled = it }
                    )
                    SettingsDivider()
                    SettingsClickable(
                        label = "الصوت (TTS)",
                        value = "افتراضي",
                        onClick = { /* TODO */ }
                    )
                }
            }

            // Integrations
            item {
                SettingsSectionHeader("🔌 التطبيقات المربوطة")
                SettingsCard {
                    IntegrationRow("▶️", "YouTube", true)
                    SettingsDivider()
                    IntegrationRow("📸", "Instagram", false)
                    SettingsDivider()
                    IntegrationRow("🐦", "Twitter / X", false)
                    SettingsDivider()
                    IntegrationRow("📧", "Gmail", false)
                    SettingsDivider()
                    IntegrationRow("➕", "إضافة تكامل جديد", null)
                }
            }

            // Account
            item {
                SettingsSectionHeader("👤 الحساب")
                SettingsCard {
                    SettingsClickable("إعدادات API", "Gemini Flash · مفعّل", {})
                    SettingsDivider()
                    SettingsClickable("سياسة الخصوصية", "", {})
                    SettingsDivider()
                    SettingsClickable("عن التطبيق", "v1.0.0", {})
                }
            }

            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}

@Composable
private fun SettingsSectionHeader(title: String) {
    Text(
        title,
        color = MOPurple,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 24.dp, top = 24.dp, bottom = 8.dp)
    )
}

@Composable
private fun SettingsCard(content: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = MOSurface
    ) {
        Column(content = content)
    }
}

@Composable
private fun SettingsToggle(
    label: String,
    sublabel: String,
    checked: Boolean,
    onChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(label, color = MOTextPrimary, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            Text(sublabel, color = MOTextTertiary, fontSize = 12.sp)
        }
        Switch(
            checked = checked,
            onCheckedChange = onChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = MOAccentGreen,
                uncheckedThumbColor = MOTextSecondary,
                uncheckedTrackColor = MOSurface2
            )
        )
    }
}

@Composable
private fun SettingsClickable(label: String, value: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = MOTextPrimary, fontSize = 15.sp, modifier = Modifier.weight(1f))
        Text(value, color = MOTextTertiary, fontSize = 13.sp)
    }
}

@Composable
private fun SettingsDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(MOSurface2)
    )
}

@Composable
private fun IntegrationRow(emoji: String, name: String, connected: Boolean?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO: OAuth */ }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(emoji, fontSize = 22.sp)
        Text(name, color = MOTextPrimary, fontSize = 15.sp, modifier = Modifier.weight(1f))
        when (connected) {
            true -> Text("✓ مربوط", color = MOAccentGreen, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            false -> Text("ربط", color = MOPurple, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            null -> Text("→", color = MOTextSecondary, fontSize = 14.sp)
        }
    }
}