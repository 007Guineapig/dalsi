package com.ahmedapps.roomdatabase.RoomDatabaza

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedapps.roomdatabase.RoomDatabaza.ReceptDao
import com.ahmedapps.roomdatabase.RoomDatabaza.ReceptState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

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
        combine(_state, isSortedByDateAdded, recepts) { state, isSortedByDateAdded, recepts ->
            state.copy(
                receptiks = recepts
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ReceptState())

}