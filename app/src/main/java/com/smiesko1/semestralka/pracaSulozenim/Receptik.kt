package com.smiesko1.semestralka.pracaSulozenim


import androidx.room.Entity
import androidx.room.PrimaryKey

//Entita ROOM databazy recept
@Entity
data class Receptik(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nazov: String,
    val popis: String,
    val obrazok:String,
    val postup:String,
    val ingrediencie:String,
    val kategoria:String



)
