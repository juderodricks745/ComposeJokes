package com.davidbronn.composejokes.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.davidbronn.composejokes.R
import com.davidbronn.composejokes.domain.model.Item

@Composable
fun JokeDialog(
    isCategory: Boolean,
    categories: MutableList<Item>,
    blackList: MutableList<Item>,
    onDismiss: () -> Unit,
    onRefresh: () -> Unit,
    updateSelection: (Boolean, Int, Boolean) -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(12.dp)
        ) {
            DialogContent(
                title = if (isCategory) stringResource(R.string.lbl_categories) else stringResource(R.string.lbl_blacklist),
                items = if (isCategory) categories else blackList,
                onUpdateSelection = { index, value -> updateSelection(isCategory, index, value) },
                onDismiss = onDismiss,
                onRefresh = onRefresh
            )
        }
    }
}

@Composable
private fun DialogContent(
    title: String,
    items: MutableList<Item>,
    onDismiss: () -> Unit,
    onRefresh: () -> Unit,
    onUpdateSelection: (Int, Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        GroupedCheckbox(items, onUpdateSelection)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                onDismiss()
                onRefresh()
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(text = stringResource(R.string.lbl_done))
        }
    }
}

@Composable
fun GroupedCheckbox(
    items: MutableList<Item>,
    updateSelection: (Int, Boolean) -> Unit,
) {
    items.forEachIndexed { index, item ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            val checkedState = remember { mutableStateOf(item.selected) }
            Checkbox(
                checked = checkedState.value,
                onCheckedChange = {
                    checkedState.value = it
                    updateSelection(index, checkedState.value)
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = item.title, color = Color.Black, style = MaterialTheme.typography.body1)
        }
    }
}

@Preview
@Composable
fun DialogContentPreview() {
    Surface {
        DialogContent(
            title = "Categories",
            items = mutableListOf(Item(id = 0, title = "Category 1", selected = true)),
            onDismiss = {},
            onRefresh = {},
            onUpdateSelection = { _, _ -> }
        )
    }
}

@Preview
@Composable
fun GroupedCheckboxPreview() {
    Surface {
        GroupedCheckbox(
            items = mutableListOf(Item(id = 0, title = "Category 1", selected = true)),
            updateSelection = { _, _ -> }
        )
    }
}
