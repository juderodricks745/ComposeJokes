package com.davidbronn.composejokes.utils

interface Mapper<T, E> {
    fun map(t: T): E
}
