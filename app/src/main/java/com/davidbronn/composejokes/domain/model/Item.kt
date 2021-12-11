package com.davidbronn.composejokes.domain.model

data class Item(
    val id: Int,
    val title: String,
    var selected: Boolean
)
