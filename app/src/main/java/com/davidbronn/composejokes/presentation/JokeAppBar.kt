package com.davidbronn.composejokes.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.davidbronn.composejokes.R

@ExperimentalMaterialApi
@Composable
fun JokeAppBar(viewModel: JokeViewModel) {
    val expandedMenu = remember { mutableStateOf(false) }
    var jokeDialogState by remember { mutableStateOf(false) }
    var readMeDialogState by remember { mutableStateOf(false) }

    var currentDialogType: DialogOptionType? by remember { mutableStateOf(null) }
    val openDialog: (DialogOptionType) -> Unit = {
        jokeDialogState = true
        currentDialogType = it
    }

    if (jokeDialogState) {
        currentDialogType?.let { dialogType ->
            JokeDialog(dialogType == DialogOptionType.Category, viewModel, onDismiss = {
                jokeDialogState = jokeDialogState.not()
            })
        }
    }

    if (readMeDialogState) {
        ReadMeDialog(onDismiss = {
            readMeDialogState = readMeDialogState.not()
        })
    }

    TopAppBar(elevation = 10.dp, backgroundColor = MaterialTheme.colors.primary) {
        Text(
            text = stringResource(R.string.lbl_jokes),
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(Modifier.weight(1f))
        Box {
            IconButton(
                modifier = Modifier.size(width = 30.dp, height = 30.dp),
                onClick = { expandedMenu.value = true }
            ) {
                Icon(Icons.Filled.MoreVert, stringResource(R.string.lbl_more_options))
            }
            DropdownMenu(
                modifier = Modifier.wrapContentSize(align = Alignment.TopEnd),
                expanded = expandedMenu.value,
                onDismissRequest = { expandedMenu.value = false }) {
                DropdownMenuItem(onClick = {
                    expandedMenu.value = false
                    openDialog(DialogOptionType.Category)
                }) {
                    Text(text = stringResource(R.string.lbl_categories), style = MaterialTheme.typography.body1)
                }
                Divider()
                DropdownMenuItem(onClick = {
                    expandedMenu.value = false
                    openDialog(DialogOptionType.BlackList)
                }) {
                    Text(text = stringResource(R.string.lbl_blacklist), style = MaterialTheme.typography.body1)
                }
                Divider()
                DropdownMenuItem(onClick = {
                    expandedMenu.value = false
                    readMeDialogState = true
                }) {
                    Text(text = stringResource(R.string.lbl_read_me), style = MaterialTheme.typography.body1)
                }
            }
        }
    }
}

sealed class DialogOptionType {
    object Category : DialogOptionType()
    object BlackList : DialogOptionType()
}