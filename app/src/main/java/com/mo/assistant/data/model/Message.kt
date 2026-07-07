package com.mo.assistant.data.model

/**
 * Represents a single message in a chat conversation.
 */
data class Message(
    val id: String = java.util.UUID.randomUUID().toString(),
    val content: String,
    val role: Role,
    val timestamp: Long = System.currentTimeMillis(),
    val isError: Boolean = false,
    val aiProvider: AIProvider? = null
)

enum class Role {
    USER,
    ASSISTANT,
    SYSTEM
}

/**
 * Supported AI providers, ordered by cascade priority.
 * 1 = default free, 2+ = upgradeable paid options.
 */
enum class AIProvider(
    val displayName: String,
    val displayNameAr: String,
    val isFree: Boolean,
    val modelId: String,
    val emoji: String
) {
    GEMINI_FLASH(
        displayName = "Gemini Flash",
        displayNameAr = "Gemini Flash",
        isFree = true,
        modelId = "gemini-2.0-flash",
        emoji = "🟢"
    ),
    MINIMAX(
        displayName = "MiniMax",
        displayNameAr = "MiniMax",
        isFree = false,
        modelId = "MiniMax",
        emoji = "🟡"
    ),
    CLAUDE(
        displayName = "Claude",
        displayNameAr = "Claude",
        isFree = false,
        modelId = "claude-3-5-sonnet-20241022",
        emoji = "🟠"
    ),
    GPT4O(
        displayName = "GPT-4o",
        displayNameAr = "GPT-4o",
        isFree = false,
        modelId = "gpt-4o",
        emoji = "🔴"
    ),
    GEMINI_PRO(
        displayName = "Gemini Pro",
        displayNameAr = "Gemini Pro",
        isFree = true,
        modelId = "gemini-2.0-pro-exp",
        emoji = "⚪"
    )
}