package com.mo.assistant.data.api

import com.google.gson.annotations.SerializedName

// ============================================
// Gemini API Request/Response Models
// https://ai.google.dev/api/generate-content
// ============================================

data class GeminiRequest(
    val contents: List<Content>,
    @SerializedName("systemInstruction")
    val systemInstruction: SystemInstruction? = null,
    val generationConfig: GenerationConfig? = null,
    @SerializedName("safetySettings")
    val safetySettings: List<SafetySetting>? = null
)

data class Content(
    val role: String,  // "user" or "model"
    val parts: List<Part>
)

data class Part(
    val text: String
)

data class SystemInstruction(
    val parts: List<Part>
)

data class GenerationConfig(
    val temperature: Float? = 0.9f,
    @SerializedName("topK")
    val topK: Int? = 40,
    @SerializedName("topP")
    val topP: Float? = 0.95f,
    @SerializedName("maxOutputTokens")
    val maxOutputTokens: Int? = 2048,
    @SerializedName("stopSequences")
    val stopSequences: List<String>? = null
)

data class SafetySetting(
    val category: String,
    val threshold: String
)

data class GeminiResponse(
    val candidates: List<Candidate>?,
    @SerializedName("usageMetadata")
    val usageMetadata: UsageMetadata? = null,
    val error: GeminiError? = null
)

data class Candidate(
    val content: Content,
    @SerializedName("finishReason")
    val finishReason: String? = null,
    val index: Int? = null
)

data class UsageMetadata(
    @SerializedName("promptTokenCount")
    val promptTokenCount: Int? = null,
    @SerializedName("candidatesTokenCount")
    val candidatesTokenCount: Int? = null,
    @SerializedName("totalTokenCount")
    val totalTokenCount: Int? = null
)

data class GeminiError(
    val code: Int? = null,
    val message: String? = null,
    val status: String? = null
)