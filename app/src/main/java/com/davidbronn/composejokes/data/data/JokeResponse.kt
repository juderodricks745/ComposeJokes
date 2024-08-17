package com.davidbronn.composejokes.data.data

import androidx.annotation.Keep

@Keep
data class JokeResponse(
    val type: String,
    val joke: String?,
    val setup: String?,
    val delivery: String?,
)