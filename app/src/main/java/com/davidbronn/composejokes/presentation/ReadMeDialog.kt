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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.davidbronn.composejokes.R

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
                    Text(text = stringResource(R.string.lbl_greetings_everyone), style = MaterialTheme.typography.subtitle1)
                    Box(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.lbl_statement_1),
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = stringResource(R.string.lbl_statement_2),
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = stringResource(R.string.lbl_statement_3),
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = stringResource(R.string.lbl_statement_4),
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(bottom = 4.dp)
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
                            Text(text = stringResource(R.string.lbl_close), modifier = Modifier.padding(vertical = 3.dp))
                        }
                    }
                }
            }
        }
    }
}