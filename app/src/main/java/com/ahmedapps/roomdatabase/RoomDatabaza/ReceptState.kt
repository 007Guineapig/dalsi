package com.ahmedapps.roomdatabase.RoomDatabaza

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class ReceptState(

    val receptiks: List<Receptik> = emptyList(),
    val nazov: MutableState<String> = mutableStateOf(""),
    val popis: MutableState<String> = mutableStateOf(""),
    val postup: MutableState<String> = mutableStateOf(""),
    val ingrediencie: MutableState<String> = mutableStateOf(""),
    val kategoria: MutableState<String> = mutableStateOf(""),
    val obrazok: MutableState<String> = mutableStateOf("")




)