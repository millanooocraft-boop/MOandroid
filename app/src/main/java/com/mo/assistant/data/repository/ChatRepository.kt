package com.mo.assistant.data.repository

import com.mo.assistant.data.api.GeminiRequest
import com.mo.assistant.data.api.GeminiService
import com.mo.assistant.data.api.GenerationConfig
import com.mo.assistant.data.api.Content
import com.mo.assistant.data.api.Part
import com.mo.assistant.data.api.SystemInstruction
import com.mo.assistant.data.model.AIProvider
import com.mo.assistant.data.model.Message
import com.mo.assistant.data.model.Role

/**
 * Repository — orchestrates AI calls with cascade logic.
 * Start with Gemini Flash (free). Falls back to other providers if needed.
 */
class ChatRepository(
    private val geminiService: GeminiService = GeminiService.create()
) {

    /**
     * Send a message and get AI response.
     * Uses the selected provider, or defaults to Gemini Flash.
     */
    suspend fun sendMessage(
        history: List<Message>,
        userMessage: String,
        provider: AIProvider = AIProvider.GEMINI_FLASH,
        language: String = "ar"
    ): Result<String> {
        return try {
            val systemPrompt = when (language) {
                "ar" -> "أنت MO، مساعد ذكي شخصي. تتكلم بالعربي بطلاقة. إجاباتك مختصرة، ذكية، ومفيدة. تتفاعل مع المستخدم بودّ وذكاء."
                else -> "You are MO, a personal AI assistant. Be concise, smart, and helpful. Respond in the language the user uses."
            }

            // Build contents from history + new message
            val contents = mutableListOf<Content>()
            contents.add(Content(role = "user", parts = listOf(Part(text = userMessage))))

            // Optionally include last N messages for context (limit to save tokens)
            history.takeLast(6).forEach { msg ->
                val role = if (msg.role == Role.USER) "user" else "model"
                contents.add(0, Content(role = role, parts = listOf(Part(text = msg.content))))
            }

            val request = GeminiRequest(
                contents = contents,
                systemInstruction = SystemInstruction(
                    parts = listOf(Part(text = systemPrompt))
                ),
                generationConfig = GenerationConfig(
                    temperature = 0.9f,
                    maxOutputTokens = 1024
                )
            )

            val response = geminiService.generateContent(
                model = provider.modelId,
                request = request
            )

            val text = response.candidates
                ?.firstOrNull()
                ?.content
                ?.parts
                ?.firstOrNull()
                ?.text

            if (text.isNullOrBlank()) {
                Result.failure(Exception("Empty response from ${provider.displayName}"))
            } else {
                Result.success(text)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Send message with automatic cascade fallback.
     * Tries each provider in order until one succeeds.
     */
    suspend fun sendMessageWithCascade(
        history: List<Message>,
        userMessage: String,
        cascadeOrder: List<AIProvider> = listOf(
            AIProvider.GEMINI_FLASH,
            AIProvider.MINIMAX,
            AIProvider.CLAUDE,
            AIProvider.GPT4O,
            AIProvider.GEMINI_PRO
        ),
        language: String = "ar"
    ): Result<Pair<String, AIProvider>> {
        var lastError: Throwable? = null

        for (provider in cascadeOrder) {
            val result = sendMessage(history, userMessage, provider, language)
            if (result.isSuccess) {
                return Result.success(result.getOrThrow() to provider)
            }
            lastError = result.exceptionOrNull()
            // Continue to next provider
        }

        return Result.failure(
            lastError ?: Exception("All AI providers failed")
        )
    }
}