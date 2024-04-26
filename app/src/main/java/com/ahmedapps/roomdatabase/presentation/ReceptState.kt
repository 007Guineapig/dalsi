package com.ahmedapps.roomdatabase.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.ahmedapps.roomdatabase.data.Receptik

data class ReceptState(

    val receptiks: List<Receptik> = emptyList(),
    val nazov: MutableState<String> = mutableStateOf(""),
    val popis: MutableState<String> = mutableStateOf(""),
    val postup: MutableState<String> = mutableStateOf(""),
    val ingrediencie: MutableState<String> = mutableStateOf(""),
    val kategoria: MutableState<String> = mutableStateOf(""),
    val obrazok: MutableState<String> = mutableStateOf("")




)