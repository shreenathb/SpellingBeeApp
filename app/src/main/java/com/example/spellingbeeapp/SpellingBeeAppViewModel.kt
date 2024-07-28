// SpellingBeeViewModel.kt
package com.example.spellingbeeapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spellingbeeapp.DictionaryResponse
import com.example.spellingbeeapp.GameManager
import com.example.spellingbeeapp.RetrofitInstance
import com.example.spellingbeeapp.Word
import kotlinx.coroutines.launch


class SpellingBeeViewModel : ViewModel() {
    private val gameManager = GameManager()

    var currentWord = mutableStateOf(Word(""))
        private set
    var word = mutableStateOf(Word(""))
        private set
    var userGuess = mutableStateOf("")
        private set

    var score = mutableStateOf(0)
        private set

    var count = mutableStateOf(0)
        private set

    var isGameOver = mutableStateOf(false)
        private set
    var startRound = mutableStateOf(true)
        private set

    private val dictionaryApi = RetrofitInstance.api

    var wordDefinition = mutableStateOf<List<DictionaryResponse>?>(null)
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set

    fun fetchWordDefinition(word: String) {
        viewModelScope.launch {
            try {
                wordDefinition.value = dictionaryApi.getWordDefinition(word)
                errorMessage.value = null
            } catch (e: Exception) {
                errorMessage.value = "Failed to fetch definition: ${e.message}"
                wordDefinition.value = null
            }
        }
    }


    fun startNewRound() {
        currentWord.value = gameManager.getRandomWord()
        startRound.value = false
    }

    fun submitGuess(guess: String) {
        val currentWordText = currentWord.value.text
        val scoreIncrement = gameManager.calculateScore(currentWordText, guess)
        score.value += scoreIncrement
        userGuess.value = guess
        count.value++
        word.value = currentWord.value

        if (count.value == 5) {
            isGameOver.value = true
        } else {
            startNewRound()
        }
    }

    fun resetGame() {
        score.value = 0
        count.value = 0
        isGameOver.value = false
        userGuess.value = ""
        startNewRound()
    }
}
