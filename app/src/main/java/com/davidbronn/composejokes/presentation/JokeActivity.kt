package com.davidbronn.composejokes.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.davidbronn.composejokes.theme.ComposeJokesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalMaterialApi
@ExperimentalAnimationApi
class JokeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeJokesTheme {
                Surface(color = MaterialTheme.colors.background) {
                    JokeComposable()
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun JokeComposable(viewModel: JokeViewModel = hiltViewModel()) {
    val state = rememberScaffoldState()
    Scaffold(
        scaffoldState = state,
        topBar = { JokeAppBar(viewModel) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.fetchJoke() },
                contentColor = Color.White
            ) {
                Icon(
                    Icons.Filled.Refresh,
                    contentDescription = "Fetches a new Joke"
                )
            }
        }
    ) {
        val jokeState by viewModel.state.collectAsState()
        when (val result = jokeState) {
            is JokeViewModel.JokeState.Data -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = result.joke.joke,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.subtitle1
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    AnimatedVisibility(visible = result.joke.isTwoPart) {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                            text = result.joke.delivery,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h5
                        )
                    }
                }
            }
            is JokeViewModel.JokeState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = result.errorMessage, fontSize = 16.sp, style = MaterialTheme.typography.body1)
                    AnimatedVisibility(visible = result.retry) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = { viewModel.fetchJoke() },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .width(150.dp)
                                .padding(vertical = 16.dp)
                        ) {
                            Text(
                                text = result.retryText,
                                modifier = Modifier.padding(vertical = 3.dp),
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                }
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

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeJokesTheme {
        JokeComposable()
    }
}