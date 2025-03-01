package com.davidbronn.composejokes.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.davidbronn.composejokes.R
import com.davidbronn.composejokes.domain.model.Item

@Composable
fun JokeAppBar(
    categories: List<Item>,
    blackList: List<Item>,
    onRefreshJoke: () -> Unit,
    onUpdateCategory: (Int, Boolean) -> Unit,
    onUpdateBlackList: (Int, Boolean) -> Unit,
) {
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
            JokeDialog(
                isCategory = dialogType == DialogOptionType.Category,
                categories = categories,
                blackList = blackList,
                onDismiss = {
                    jokeDialogState = false
                },
                onRefresh = {
                    jokeDialogState = false
                    onRefreshJoke()
                },
                updateSelection = { isCategory, index, value ->
                    if (isCategory) {
                        onUpdateCategory(index, value)
                    } else {
                        onUpdateBlackList(index, value)
                    }
                }
            )
        }
    }

    if (readMeDialogState) {
        ReadMeDialog(onDismiss = {
            readMeDialogState = false
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
                    Text(
                        text = stringResource(R.string.lbl_categories),
                        style = MaterialTheme.typography.body1
                    )
                }
                Divider()
                DropdownMenuItem(onClick = {
                    expandedMenu.value = false
                    openDialog(DialogOptionType.BlackList)
                }) {
                    Text(
                        text = stringResource(R.string.lbl_blacklist),
                        style = MaterialTheme.typography.body1
                    )
                }
                Divider()
                DropdownMenuItem(onClick = {
                    expandedMenu.value = false
                    readMeDialogState = true
                }) {
                    Text(
                        text = stringResource(R.string.lbl_read_me),
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}

sealed class DialogOptionType {
    data object Category : DialogOptionType()
    data object BlackList : DialogOptionType()
}

