package com.smiesko1.semestralka.obrazovky

//Reprezentacia všetkych screenov, a ich cesty
sealed class Screen(val rout:String) {
    object ReceptsScreen: Screen("receptsScreen")
    object Recept1: Screen("Recept1")
    object Uvod: Screen("Uvod")
    object ReceptScreenOblubene: Screen("Oblubene")
    object PridajRecept: Screen("Pridaj")

}