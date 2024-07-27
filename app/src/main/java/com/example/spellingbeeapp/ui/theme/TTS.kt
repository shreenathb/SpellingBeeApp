package com.example.spellingbeeapp.ui.theme

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale


class TTS(context: Context, onInit: (Boolean) -> Unit) {
    private var tts: TextToSpeech? = null

    init {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale.US
                onInit(true)
            } else {
                onInit(false)
            }
        }
    }

    fun speak(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    fun stop() {
        tts?.stop()
    }

    fun shutdown() {
        tts?.shutdown()
    }
}