package com.davidbronn.composejokes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidbronn.composejokes.R
import com.davidbronn.composejokes.domain.model.Item
import com.davidbronn.composejokes.domain.model.Joke
import com.davidbronn.composejokes.domain.repository.IJokeRepository
import com.davidbronn.composejokes.utils.Constants
import com.davidbronn.composejokes.utils.Resource
import com.davidbronn.composejokes.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class JokeViewModel @Inject constructor(
    private val repository: IJokeRepository
) : ViewModel() {

    var category = mutableListOf<Item>()
    var blacklist = mutableListOf<Item>()
    private val _state = MutableStateFlow<JokeState>(JokeState.Loading)
    val state: StateFlow<JokeState> = _state

    init {
        createCategory()
        createBlacklist()
        fetchJoke()
    }

    // region [CREATE CATEGORY & BLACKLIST]
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
    // endregion

    fun updateCategory(index: Int, selection: Boolean) {
        category[index].selected = selection
    }

    fun updateBlackList(index: Int, selection: Boolean) {
        blacklist[index].selected = selection
    }

    fun fetchJoke() {
        val categorySelected = category.filter { it.selected }.joinToString(",") { it.title.lowercase() }
        var blackListSelected = blacklist.filter { it.selected }.joinToString(",") { it.title.lowercase() }
        blackListSelected = blackListSelected.ifBlank { "" }
        if (categorySelected.isBlank()) {
            _state.value = JokeState.Error(UiText.ResourceText(R.string.lbl_select_category), false)
            return
        }
        viewModelScope.launch {
            _state.value = JokeState.Loading
            repository.getJoke(categorySelected, blackListSelected).collect { resource ->
                when(resource) {
                    is Resource.Error -> {
                        _state.value = JokeState.Error(UiText.DynamicText(resource.message), true)
                    }
                    is Resource.Success -> {
                        _state.value = JokeState.Data(resource.data)
                    }
                }
            }
        }
    }

    sealed class JokeState {
        object Loading: JokeState()
        data class Data(val joke: Joke) : JokeState()
        data class Error(val errorMessage: UiText, val retry: Boolean) : JokeState()
    }
}