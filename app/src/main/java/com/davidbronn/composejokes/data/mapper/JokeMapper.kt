package com.davidbronn.composejokes.data.mapper

import com.davidbronn.composejokes.data.data.JokeResponse
import com.davidbronn.composejokes.domain.model.Joke
import com.davidbronn.composejokes.utils.Mapper

class JokeMapper : Mapper<JokeResponse, Joke> {

    override fun map(t: JokeResponse): Joke {
        return Joke(t.type == "twopart", t.joke ?: t.setup ?: "", t.delivery ?: "")
    }
}