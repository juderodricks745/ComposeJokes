package com.davidbronn.composejokes.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.davidbronn.composejokes.R
import com.davidbronn.composejokes.domain.model.Item

@ExperimentalMaterialApi
@Composable
fun JokeDialog(
    isCategory: Boolean,
    viewModel: JokeViewModel,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(12.dp)
        ) {
            Box {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = if (isCategory) stringResource(R.string.lbl_categories) else stringResource(R.string.lbl_blacklist),
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
                        fontSize = 20.sp
                    )
                    GroupedCheckbox(isCategory, viewModel, if (isCategory) viewModel.category else viewModel.blacklist)
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                onDismiss()
                                viewModel.fetchJoke()
                            },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .width(150.dp)
                                .padding(vertical = 16.dp)
                        ) {
                            Text(text = stringResource(R.string.lbl_done), modifier = Modifier.padding(vertical = 3.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GroupedCheckbox(
    isCategory: Boolean,
    viewModel: JokeViewModel,
    checkboxItems: MutableList<Item>,
) {
    checkboxItems.forEachIndexed { index, item ->
        Row {
            val checkedState = remember { mutableStateOf(item.selected) }
            Checkbox(
                checked = checkedState.value,
                onCheckedChange = {
                    checkedState.value = it
                    if (isCategory) {
                        viewModel.updateCategory(index, checkedState.value)
                    } else {
                        viewModel.updateBlackList(index, checkedState.value)
                    }
                },
            )
            Text(text = item.title, modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}