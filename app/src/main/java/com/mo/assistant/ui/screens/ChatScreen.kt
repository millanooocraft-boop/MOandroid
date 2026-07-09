package com.mo.assistant.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mo.assistant.data.model.AIProvider
import com.mo.assistant.data.model.Message
import com.mo.assistant.data.model.Role
import com.mo.assistant.ui.components.MessageBubble
import com.mo.assistant.ui.components.TypingIndicator
import com.mo.assistant.ui.theme.MOAccentGreen
import com.mo.assistant.ui.theme.MOBgDark
import com.mo.assistant.ui.theme.MOBgDark2
import com.mo.assistant.ui.theme.MOPurple
import com.mo.assistant.ui.theme.MOPurpleDeep
import com.mo.assistant.ui.theme.MOSurface
import com.mo.assistant.ui.theme.MOTextPrimary
import com.mo.assistant.ui.theme.MOTextTertiary
import com.mo.assistant.ui.viewmodel.ChatViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    onBack: () -> Unit,
    viewModel: ChatViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    var inputText by remember { mutableStateOf("") }
    val keyboard = LocalSoftwareKeyboardController.current

    // Auto-scroll to bottom on new messages
    LaunchedEffect(state.messages.size) {
        if (state.messages.isNotEmpty()) {
            listState.animateScrollToItem(state.messages.size - 1)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MOBgDark
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MOBgDark2)
                    .padding(WindowInsets.statusBars.asPaddingValues())
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MOTextPrimary
                    )
                }
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            Brush.linearGradient(listOf(MOPurple, MOAccentGreen)),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "M",
                        fontWeight = FontWeight.Black,
                        color = MOBgDark,
                        fontSize = 14.sp
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "MO",
                        color = MOTextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    val providerLabel = state.currentProvider.displayNameAr
                    Text(
                        text = if (state.cascadeEnabled) "● Auto Cascade" else "● $providerLabel",
                        color = MOAccentGreen,
                        fontSize = 11.sp
                    )
                }
                // AI selector button
                AIProviderChip(
                    provider = state.currentProvider,
                    onClick = { /* show picker */ }
                )
            }

            // Messages
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                state = listState,
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.messages.size) = { index ->
                    val msg = state.messages[index]
                }
                if (state.isLoading) {
                    item { TypingIndicator() }
                }
            }

            // Input
            Surface(
                color = MOBgDark2,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(WindowInsets.ime.asPaddingValues())
                        .padding(horizontal = 12.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        modifier = Modifier.weight(1f),
                        placeholder = {
                            Text(
                                "اكتب رسالتك...",
                                color = MOTextTertiary,
                                fontSize = 14.sp
                            )
                        },
                        shape = RoundedCornerShape(50),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MOSurface,
                            unfocusedContainerColor = MOSurface,
                            focusedBorderColor = MOPurple,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        maxLines = 4,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                        keyboardActions = KeyboardActions(
                            onSend = {
                                if (inputText.isNotBlank()) {
                                    viewModel.sendMessage(inputText)
                                    inputText = ""
                                    keyboard?.hide()
                                }
                            }
                        )
                    )

                    // Voice mic button
                    IconButton(
                        onClick = { /* TODO: voice input */ },
                        modifier = Modifier
                            .size(48.dp)
                            .background(MOSurface, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Mic,
                            contentDescription = "Voice",
                            tint = MOPurple
                        )
                    }

                    // Send button
                    IconButton(
                        onClick = {
                            if (inputText.isNotBlank()) {
                                viewModel.sendMessage(inputText)
                                inputText = ""
                                keyboard?.hide()
                            }
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                Brush.linearGradient(listOf(MOPurple, MOPurpleDeep)),
                                CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AIProviderChip(provider: AIProvider, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(50),
        color = MOSurface,
        modifier = Modifier.clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(provider.emoji, fontSize = 12.sp)
            Text(
                provider.displayNameAr,
                color = MOTextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
