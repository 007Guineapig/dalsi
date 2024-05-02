package com.ahmedapps.roomdatabase.Obrazovky
sealed class Screen(val rout:String) {
    object ReceptsScreen: Screen("notesScreen")
    object Recept1: Screen("Recept1")
    object Uvod: Screen("Uvod")
    object ReceptScreenOblubene: Screen("Oblubene")

}