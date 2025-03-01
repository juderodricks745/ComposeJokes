package com.davidbronn.composejokes.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidbronn.composejokes.R
import com.davidbronn.composejokes.components.JokeError
import org.koin.androidx.compose.koinViewModel

/**
 * Created by Jude on 17/July/2022
 */

@Composable
fun JokesScreen(viewModel: JokeViewModel = koinViewModel<JokeViewModel>()) {

    val state = rememberScaffoldState()
    val jokeState by viewModel.state.collectAsState()

    Scaffold(
        scaffoldState = state,
        topBar = {
            JokeAppBar(
                categories = viewModel.category,
                blackList = viewModel.blacklist,
                onRefreshJoke = { viewModel.fetchJoke() },
                onUpdateCategory = { index, value -> viewModel.updateCategory(index, value) },
                onUpdateBlackList = { index, value -> viewModel.updateBlackList(index, value) },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.fetchJoke() },
                contentColor = Color.White
            ) {
                Icon(
                    Icons.Filled.Refresh,
                    contentDescription = stringResource(R.string.lbl_fetch_new_joke)
                )
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            when (val result = jokeState) {
                is JokeViewModel.JokeState.Data -> {
                    JokeView(
                        joke = result.joke.joke,
                        delivery = result.joke.delivery,
                        isTowPartJoke = result.joke.isTwoPart
                    )
                }

                is JokeViewModel.JokeState.Error -> {
                    JokeError(
                        retry = result.retry,
                        onRetry = { viewModel.fetchJoke() },
                        errorMessage = result.errorMessage.asString(),
                    )
                }

                JokeViewModel.JokeState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun JokeView(
    joke: String,
    delivery: String,
    isTowPartJoke: Boolean,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = joke,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        Spacer(modifier = Modifier.height(10.dp))
        AnimatedVisibility(visible = isTowPartJoke) {
            Text(
                text = delivery,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }
    }
}

@Preview
@Composable
fun JokeViewSinglePreview() {
    Surface {
        JokeView(
            joke = "I have a joke about Stack Overflow, but you would day it's a duplicate",
            delivery = "",
            isTowPartJoke = false
        )
    }
}

@Preview
@Composable
fun JokeViewTwoPartPreview() {
    Surface {
        JokeView(
            joke = "Why did the developer go broke?",
            delivery = "Because he used up all his cache",
            isTowPartJoke = true
        )
    }
}