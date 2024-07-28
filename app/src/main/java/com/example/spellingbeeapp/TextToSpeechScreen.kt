package com.example.spellingbeeapp

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.spellingbeeapp.ui.theme.TTS
import com.example.spellingbeeapp.viewmodel.SpellingBeeViewModel

@Composable
fun TextToSpeechScreen(ttsHelper: TTS, isTtsInitialized: Boolean, viewModel: SpellingBeeViewModel) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val currentWord by viewModel.currentWord
    val userGuess by viewModel.userGuess
    val score by viewModel.score
    val count by viewModel.count
    val isGameOver by viewModel.isGameOver
    val word by viewModel.word
    val wordDefinition by  viewModel.wordDefinition
    val errorMessage by  viewModel.errorMessage

    var text by remember { mutableStateOf(TextFieldValue("")) }

    if (isGameOver) {
        Toast.makeText(context, "Game Over! Your final score is $score!!", Toast.LENGTH_LONG).show()
    }

    if(viewModel.startRound.value){
        viewModel.startNewRound()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Your guess: $userGuess")
        Text("Actual word: ${word.text}")
        Text("Score: $score")
        Text("Words: $count/5")

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Enter word") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(
                onClick = {
                    if (isTtsInitialized && !isGameOver) {
                        ttsHelper.speak(currentWord.text)
                    }
                },
                enabled = isTtsInitialized
            ) {
                Text("Listen to the word")
            }

            Button(
                onClick = {
                    if (!isGameOver) {
                        viewModel.submitGuess(text.text.lowercase())
                        text = TextFieldValue("")
                    }
                }
            ) {
                Text("Submit")
            }
        }

        Button(onClick = {
            viewModel.fetchWordDefinition(currentWord.text)
        }) {
            Text("Define")
        }

        when {
            wordDefinition != null -> {
                wordDefinition!!.forEach { dictionaryResponse ->
                    dictionaryResponse.meanings.forEach { meaning ->
                        Text("Part of Speech: ${meaning.partOfSpeech}")
                        meaning.definitions.forEach { definition ->
                            Text("Definition: ${definition.definition}")
                            definition.example?.let {
                                Text("Example: $it")
                            }
                        }
                    }
                }
            }
            errorMessage != null -> {
                Text("Error: $errorMessage")
            }
        }

        if (isGameOver) {
            Button(onClick = {
                viewModel.resetGame()
            }) {
                Text("Retry")
            }
        }

        if (!isTtsInitialized) {
            Text("Text-to-Speech is initializing...")
        }
    }
}