package com.mo.assistant.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mo.assistant.data.model.AIProvider
import com.mo.assistant.data.model.Message
import com.mo.assistant.data.model.Role
import com.mo.assistant.data.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ChatUiState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false,
    val currentProvider: AIProvider = AIProvider.GEMINI_FLASH,
    val cascadeEnabled: Boolean = true,
    val language: String = "ar",
    val errorMessage: String? = null
)

/**
 * ViewModel for the chat screen.
 * Manages conversation state, calls the repository, exposes UI state.
 */
class ChatViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ChatRepository()

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        // Welcome message
        _uiState.update {
            it.copy(
                messages = listOf(
                    Message(
                        content = "أهلاً بك! 👋 أنا MO، مساعدك الذكي.\nقول لي إيش تحتاج وأساعدك.",
                        role = Role.ASSISTANT,
                        aiProvider = AIProvider.GEMINI_FLASH
                    )
                )
            )
        }
    }

    fun sendMessage(text: String) {
        if (text.isBlank()) return

        val userMsg = Message(content = text.trim(), role = Role.USER)
        _uiState.update {
            it.copy(
                messages = it.messages + userMsg,
                isLoading = true,
                errorMessage = null
            )
        }

        viewModelScope.launch {
            val current = _uiState.value
            val result = if (current.cascadeEnabled) {
                repository.sendMessageWithCascade(
                    history = current.messages,
                    userMessage = text,
                    language = current.language
                )
            } else {
                repository.sendMessage(
                    history = current.messages,
                    userMessage = text,
                    provider = current.currentProvider,
                    language = current.language
                ).map { it to current.currentProvider }
            }

            result.fold(
                onSuccess = { (response, provider) ->
                    val aiMsg = Message(
                        content = response,
                        role = Role.ASSISTANT,
                        aiProvider = provider
                    )
                    _uiState.update {
                        it.copy(
                            messages = it.messages + aiMsg,
                            isLoading = false,
                            currentProvider = provider
                        )
                    }
                },
                onFailure = { error ->
                    val errorMsg = Message(
                        content = "⚠️ عذراً، صار خطأ: ${error.message ?: "غير معروف"}",
                        role = Role.ASSISTANT,
                        isError = true
                    )
                    _uiState.update {
                        it.copy(
                            messages = it.messages + errorMsg,
                            isLoading = false,
                            errorMessage = error.message
                        )
                    }
                }
            )
        }
    }

    fun setProvider(provider: AIProvider) {
        _uiState.update { it.copy(currentProvider = provider, cascadeEnabled = false) }
    }

    fun toggleCascade() {
        _uiState.update { it.copy(cascadeEnabled = !it.cascadeEnabled) }
    }

    fun setLanguage(language: String) {
        _uiState.update { it.copy(language = language) }
    }

     fun clearChat() {
        _uiState.update {
            it.copy(
                messages = listOf(
                    Message(
                        content = "أهلاً بك! 👋 أنا MO، مساعدك الذكي.\nقول لي إيش تحتاج وأساعدك.",
                        role = Role.ASSISTANT,
                        aiProvider = AIProvider.GEMINI_FLASH
                    )
                )
            )
        }
    }

    private fun <T, R> Result<T>.map(transform: (T) -> R): Result<R> {
        return fold(
            onSuccess = { Result.success(transform(it)) },
            onFailure = { Result.failure(it) }
        )
    }
}
