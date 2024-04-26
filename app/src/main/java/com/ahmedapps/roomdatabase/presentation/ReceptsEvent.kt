package com.ahmedapps.roomdatabase.presentation

import com.ahmedapps.roomdatabase.data.Receptik

sealed interface ReceptsEvent {
    object SortRecepts: ReceptsEvent

    data class DeleteRecept(val receptik: Receptik): ReceptsEvent

    data class SaveRecept(
        val nazov: String,
        val popis: String,
        val obrazok:String,
        val postup:String,
        val ingrediencie:String,
        val kategoria:String,
    ): ReceptsEvent
}
