// SpellingBeeViewModel.kt
package com.example.spellingbeeapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.spellingbeeapp.GameManager
import com.example.spellingbeeapp.Word


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
