package com.example.spellingbeeapp

import androidx.compose.runtime.Composable
import java.lang.Integer.min


@Composable
fun GameLogic(){
    val spellingBeeWords = arrayOf(
        // Championship Words
        "Acquiesce", "Autochthonous", "Bourgeoisie", "Cymotrichous", "Eudaemonic", "Guetapens",
        "Laodicean", "Logorrhea", "Marocain", "Pendeloque", "Propylon", "Schwarmerei",
        "Stichomythia", "Smaragdine", "Ursprache",

        // Final Round Words
        "Abecedarius", "Appoggiatura", "Coulis", "Esquamulose", "Feldsher", "Gesellschaft",
        "Hodiernal", "Insouciant", "Knaidel", "Logorrheic", "Nunatak", "Pococurante",
        "Promiscuous", "Rhabdomancy", "Tetragrammaton",

        // Preliminary Round Words
        "Apocryphal", "Banausic", "Cachinnate", "Defenestration", "Ebullience", "Fenestration",
        "Garrulous", "Heliophile", "Illimitable", "Jalousie", "Kerfuffle", "Lugubrious",
        "Munificent", "Nociceptor", "Obfuscatory"
    )


}


fun minEditDistance(word1:String, word2:String) : Int{
    var dp = Array(word1.length + 1){Array(word2.length+1){0} }

    for(i in 0..word1.length){
        dp[i][0] = i
    }

    for(j in 0..word2.length){
        dp[0][j] = j
    }

    for(i in 1..word1.length){
        for(j in 1..word2.length){
            if(word1[i-1] == word2[j-1]){
                dp[i][j] = dp[i-1][j-1];
            }else{
                dp[i][j] = minOf(dp[i][j - 1], dp[i - 1][j], dp[i - 1][j - 1]) + 1
            }
        }
    }

    return dp[word1.length][word2.length]
}