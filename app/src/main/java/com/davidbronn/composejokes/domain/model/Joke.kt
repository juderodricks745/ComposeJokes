package com.davidbronn.composejokes.domain.model

data class Joke(
    val isTwoPart: Boolean,
    val joke: String,
    val delivery: String,
)
