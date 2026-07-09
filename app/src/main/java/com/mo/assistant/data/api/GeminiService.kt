package com.mo.assistant.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GeminiService {
    @POST("v1beta/models/{model}:generateContent")
    suspend fun generateContent(
        @Path("model") model: String,
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): GeminiResponse

    companion object {
        private const val BASE_URL = "https://generativelanguage.googleapis.com/"
        private const val DEFAULT_KEY = "REPLACE_WITH_YOUR_KEY"

        fun create(): GeminiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GeminiService::class.java)
        }

        fun getApiKey(): String = DEFAULT_KEY
    }
}
