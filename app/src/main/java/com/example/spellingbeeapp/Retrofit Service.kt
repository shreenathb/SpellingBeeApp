package com.example.spellingbeeapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApiService {
    @GET("entries/en/{word}")
    suspend fun getWordDefinition(@Path("word") word: String): List<DictionaryResponse>
}

data class DictionaryResponse(val word: String, val meanings: List<Meaning>)

data class Meaning(val partOfSpeech: String, val definitions: List<Definition>)

data class Definition(val definition: String, val example: String?)


object RetrofitInstance {
    private const val BASE_URL = "https://api.dictionaryapi.dev/api/v2/"

    val api: DictionaryApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApiService::class.java)
    }
}