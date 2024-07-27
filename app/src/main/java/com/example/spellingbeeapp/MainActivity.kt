package com.example.spellingbeeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import com.example.spellingbeeapp.ui.theme.SpellingBeeAppTheme
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spellingbeeapp.ui.theme.TTS
import com.example.spellingbeeapp.viewmodel.SpellingBeeViewModel

class MainActivity : ComponentActivity() {
    private lateinit var ttsHelper: TTS
    private var isTtsInitialized by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ttsHelper = TTS(this) { success ->
            isTtsInitialized = success
        }
        setContent {
            val viewModel: SpellingBeeViewModel = viewModel()
            SpellingBeeAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    TextToSpeechScreen(ttsHelper, isTtsInitialized, viewModel)
                }
            }
        }
    }
}