package com.davidbronn.composejokes.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davidbronn.composejokes.R

@Composable
fun JokeError(
    retry: Boolean,
    errorMessage: String,
    onRetry: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = errorMessage, fontSize = 16.sp, style = MaterialTheme.typography.body1)
        AnimatedVisibility(visible = retry) {
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = { onRetry.invoke() },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .width(150.dp)
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.lbl_retry),
                    modifier = Modifier.padding(vertical = 3.dp),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

@Preview
@Composable
fun JokeErrorPreview(modifier: Modifier = Modifier) {
    JokeError(
        retry = true,
        errorMessage = "Something went wrong",
        onRetry = {}
    )
}