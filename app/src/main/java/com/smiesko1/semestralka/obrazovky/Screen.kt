package com.smiesko1.semestralka.obrazovky
sealed class Screen(val rout:String) {
    object ReceptsScreen: Screen("notesScreen")
    object Recept1: Screen("Recept1")
    object Uvod: Screen("Uvod")
    object ReceptScreenOblubene: Screen("Oblubene")
    object PridajRecept: Screen("Pridaj")

}