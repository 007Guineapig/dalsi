package com.smiesko1.semestralka.pracaSulozenim

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

class ReceptsViewModel(
    private val dao: ReceptDao
) : ViewModel() {

    private val recepts = dao.getReceptsOrderdByTitle()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val _state = MutableStateFlow(ReceptState())
    val state = combine(_state, recepts) { state, recepts ->
        state.copy(
            receptiks = recepts
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ReceptState())


}