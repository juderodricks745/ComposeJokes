package com.davidbronn.composejokes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidbronn.composejokes.R
import com.davidbronn.composejokes.domain.model.Item
import com.davidbronn.composejokes.domain.model.Joke
import com.davidbronn.composejokes.domain.repository.IJokeRepository
import com.davidbronn.composejokes.utils.Constants
import com.davidbronn.composejokes.utils.Result
import com.davidbronn.composejokes.utils.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class JokeViewModel(
    private val repository: IJokeRepository
) : ViewModel() {

    var category = listOf<Item>()
    var blacklist = listOf<Item>()
    private val _state = MutableStateFlow<JokeState>(JokeState.Loading)
    val state: StateFlow<JokeState> = _state

    init {
        createCategory()
        createBlacklist()
        fetchJoke()
    }

    // region [CATEGORY & BLACKLIST OPERATIONS]
    private fun createCategory() {
        category = Constants.category.mapIndexed { index, s ->
            Item(index, s, index == 0)
        }.toMutableList()
    }

    private fun createBlacklist() {
        blacklist = Constants.blackList.mapIndexed { index, s ->
            Item(index, s, true)
        }.toMutableList()
    }

    private fun List<Item>.getSelectedItemsAsString(): String {
        return this.filter { it.selected }.joinToString(",") { it.title.lowercase() }
    }
    // endregion

    fun updateCategory(index: Int, selection: Boolean) {
        category[index].selected = selection
    }

    fun updateBlackList(index: Int, selection: Boolean) {
        blacklist[index].selected = selection
    }

    fun fetchJoke() {
        val selectedCategories = category.getSelectedItemsAsString()
        val selectedBlacklist = blacklist.getSelectedItemsAsString().ifEmpty { "" }
        if (selectedCategories.isBlank()) {
            _state.value = JokeState.Error(UiText.ResourceText(R.string.lbl_select_category), false)
            return
        }
        _state.value = JokeState.Loading
        viewModelScope.launch {
            _state.value = when (val result = repository.getJoke(selectedCategories, selectedBlacklist)) {
                is Result.Error -> {
                    JokeState.Error(UiText.StringText(result.message), retry = true)
                }

                is Result.Success -> {
                    JokeState.Data(result.data)
                }
            }
        }
    }

    sealed class JokeState {
        data object Loading : JokeState()
        data class Data(val joke: Joke) : JokeState()
        data class Error(val errorMessage: UiText, val retry: Boolean) : JokeState()
    }
}