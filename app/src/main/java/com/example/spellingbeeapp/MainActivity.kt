package com.example.spellingbeeapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.spellingbeeapp.ui.theme.SpellingBeeAppTheme
import com.example.spellingbeeapp.ui.theme.TTS
import com.example.spellingbeeapp.ui.theme.minEditDistance
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    private lateinit var ttsHelper: TTS
    private var isTtsInitialized by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ttsHelper = TTS(this) { success ->
            isTtsInitialized = success
        }
        setContent {
            SpellingBeeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TextToSpeechScreen(ttsHelper, isTtsInitialized)
                }
            }
        }
    }
}

@Composable
fun TextToSpeechScreen(ttsHelper: TTS, isTtsInitialized: Boolean) {
//    val spellingBeeWords = arrayOf(
//        // Championship Words
//        "Acquiesce", "Autochthonous", "Bourgeoisie", "Cymotrichous", "Eudaemonic", "Guetapens",
//        "Laodicean", "Logorrhea", "Marocain", "Pendeloque", "Propylon", "Schwarmerei",
//        "Stichomythia", "Smaragdine", "Ursprache",
//
//        // Final Round Words
//        "Abecedarius", "Appoggiatura", "Coulis", "Esquamulose", "Feldsher", "Gesellschaft",
//        "Hodiernal", "Insouciant", "Knaidel", "Logorrheic", "Nunatak", "Pococurante",
//        "Promiscuous", "Rhabdomancy", "Tetragrammaton",
//
//        // Preliminary Round Words
//        "Apocryphal", "Banausic", "Cachinnate", "Defenestration", "Ebullience", "Fenestration",
//        "Garrulous", "Heliophile", "Illimitable", "Jalousie", "Kerfuffle", "Lugubrious",
//        "Munificent", "Nociceptor", "Obfuscatory"
//    )
    val spellingBeeWords = arrayOf(
        "Acrimonious",
        "Blasphemous",
        "Cumbersome",
        "Deleterious",
        "Ebullient",
        "Flabbergasted",
        "Gregarious",
        "Hapless",
        "Impetuous",
        "Judicious",
        "Knack",
        "Lamentable",
        "Meticulous",
        "Nefarious",
        "Obstinate",
        "Perspicacious",
        "Querulous",
        "Recalcitrant",
        "Sagacious",
        "Tenacious",
        "Ubiquitous",
        "Voracious",
        "Winsome",
        "Xenial",
        "Yielding",
        "Zealous"
    )


    val setOfWords = spellingBeeWords.toHashSet()

    var text by remember { mutableStateOf(TextFieldValue("")) }
    var minDist = 0
    var score by remember {
        mutableStateOf(0)
    }

    var currentWord by remember{
        mutableStateOf("")
    }
    var userGuess by remember{
        mutableStateOf("")
    }
    var word by remember{
        mutableStateOf("")
    }
    var isSubmitted by remember{
        mutableStateOf(true)
    }
    var enable by remember{
        mutableStateOf(true)
    }
    var count by remember {
        mutableStateOf(0)
    }


    if(count == 5){
        Toast.makeText(LocalContext.current, "Game Over! Your final score is ${score}!!", Toast.LENGTH_LONG).show()
        enable = false
    }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Your guess: ${userGuess}")
        Text("Actual word: ${currentWord}")


        Text("Score: ${score}")

        Text("Words: ${count}/5")



        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Enter word") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,


        )

        Spacer(modifier = Modifier.height(16.dp))


        Row{
            Button(
                onClick = {
                    if (isTtsInitialized && enable) {

                        if (isSubmitted){
                            word = spellingBeeWords[Random.nextInt(spellingBeeWords.size)]

                            while(!setOfWords.contains(word)){
                                word = spellingBeeWords[Random.nextInt(spellingBeeWords.size)]
                            }
                            setOfWords.remove(word)
                            ttsHelper.speak(word)
                            isSubmitted = false
                        }else{
                            ttsHelper.speak(word)
                        }

                    }
                },
                enabled = isTtsInitialized
            ) {
                Text("Listen to the word")
            }

            Button(
                onClick = {
                if(enable){
                    isSubmitted = true
                    minDist = minEditDistance(word, text.text)
                    userGuess = text.text
                    score += word.length - minDist
                    text = TextFieldValue("")
                    currentWord = word.lowercase()
                    count++
                }
            } ) {
                Text("Submit")
            }

        }

        if(!enable){
            Button(onClick = {
                enable = true
                isSubmitted = true
                count = 0
                score = 0
                currentWord = ""
                userGuess = ""
                word = ""
            }) {
                Text("Retry")
            }
        }

        if (!isTtsInitialized) {
            Text("Text-to-Speech is initializing...")

        }

    }
}
