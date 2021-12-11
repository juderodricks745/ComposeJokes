package com.davidbronn.composejokes.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun ReadMeDialog(onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = false, dismissOnBackPress = false)
    ) {
        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(12.dp)
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                Column(horizontalAlignment = Alignment.Start) {
                    Text(text = "Greetings Everyone", style = MaterialTheme.typography.subtitle1)
                    Box(modifier = Modifier.height(16.dp))
                    Text(
                        text = "This application is created to display \"Programming\" related jokes.\n",
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        text = "In addition, it also has other jokes which are offensive\n",
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        text = "The primary goal of this application is to learn Jetpack Compose.\n",
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        text = "It is not the developer's intention to hurt feelings or cause discomfort to anyone.",
                        style = MaterialTheme.typography.body1
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = { onDismiss() },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .width(150.dp)
                                .padding(vertical = 16.dp)
                        ) {
                            Text(text = "Close", modifier = Modifier.padding(vertical = 3.dp))
                        }
                    }
                }
            }
        }
    }
}