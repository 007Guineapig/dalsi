package com.ahmedapps.roomdatabase.theme
sealed class Screen(val rout:String) {
    object ReceptsScreen:Screen("notesScreen")
    object Recept1:Screen("Recept1")
    object LastScreen:Screen("LastScreen")
    object Uvod:Screen("Uvod")

}