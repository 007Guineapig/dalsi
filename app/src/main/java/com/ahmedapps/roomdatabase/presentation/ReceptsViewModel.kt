package com.ahmedapps.roomdatabase.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedapps.roomdatabase.data.ReceptDao
import com.ahmedapps.roomdatabase.data.Receptik
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReceptsViewModel(
    private val dao: ReceptDao
) : ViewModel() {

    private val isSortedByDateAdded = MutableStateFlow(true)

    private var recepts =
        isSortedByDateAdded.flatMapLatest { sort ->
            if (sort) {
               dao.getNotesOrderdByTitle()
            } else {
                dao.getNotesOrderdByTitle()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val _state = MutableStateFlow(ReceptState())
    val state =
        combine(_state, isSortedByDateAdded, recepts) { state, isSortedByDateAdded, notes ->
            state.copy(
                receptiks = notes
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ReceptState())

    fun onEvent(event: ReceptsEvent) {
        when (event) {
            is ReceptsEvent.SaveRecept -> {
                val receptik = Receptik(
                    nazov = state.value.nazov.value,
                    popis = state.value.popis.value,
                    obrazok = state.value.obrazok.value,
                    postup= state.value.postup.value,
                    ingrediencie = state.value.ingrediencie.value,
                    kategoria = state.value.ingrediencie.value

                )

                viewModelScope.launch {
                    dao.upsertRecept(receptik)
                }

                _state.update {
                    it.copy(
                        nazov = mutableStateOf(""),
                        popis = mutableStateOf(""),
                        postup = mutableStateOf(""),
                        ingrediencie = mutableStateOf(""),

                    )
                }
            }
            ReceptsEvent.SortRecepts -> {
                isSortedByDateAdded.value = !isSortedByDateAdded.value
            }

        }
    }

}