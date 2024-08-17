package com.davidbronn.composejokes.domain.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Joke(
    @SerializedName("isTwoPart")
    val isTwoPart: Boolean,
    @SerializedName("joke")
    val joke: String,
    @SerializedName("delivery")
    val delivery: String,
)
