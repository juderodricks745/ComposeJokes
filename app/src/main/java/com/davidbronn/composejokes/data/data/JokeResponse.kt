package com.davidbronn.composejokes.data.data

data class JokeResponse(
    val type: String,
    val joke: String?,
    val setup: String?,
    val delivery: String?,
)