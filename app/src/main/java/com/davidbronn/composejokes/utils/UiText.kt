package com.davidbronn.composejokes.utils

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

/**
 * Created by Jude on 17/July/2022
 */
sealed class UiText {
    data class StringText(val text: String) : UiText()
    data class ResourceText(@StringRes val resId: Int) : UiText()

    @Composable
    fun asString(): String {
        return when(this) {
            is StringText -> text
            is ResourceText -> stringResource(id = resId)
        }
    }
}