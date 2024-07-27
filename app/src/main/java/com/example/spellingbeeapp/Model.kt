package com.example.spellingbeeapp


import kotlin.random.Random

data class Word(
    val text: String
)


class GameManager {
    private val spellingBeeWords = arrayOf(
        "Acrimonious", "Blasphemous", "Cumbersome", "Deleterious", "Ebullient",
        "Flabbergasted", "Gregarious", "Hapless", "Impetuous", "Judicious", "Knack",
        "Lamentable", "Meticulous", "Nefarious", "Obstinate", "Perspicacious",
        "Querulous", "Recalcitrant", "Sagacious", "Tenacious", "Ubiquitous",
        "Voracious", "Winsome", "Xenial", "Yielding", "Zealous"
    )

    private val setOfWords = spellingBeeWords.toHashSet()

    fun getRandomWord(): Word {
        var word = spellingBeeWords[Random.nextInt(spellingBeeWords.size)]
        while (!setOfWords.contains(word)) {
            word = spellingBeeWords[Random.nextInt(spellingBeeWords.size)]
        }
        setOfWords.remove(word)
        return Word(word.lowercase())
    }

    fun calculateScore(word: String, userGuess: String): Int {
        val minDist = minEditDistance(word, userGuess)
        return word.length - minDist
    }
}
