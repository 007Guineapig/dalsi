package com.smiesko1.semestralka.obrazovky

//definuje rôzne obrazovky (screeny) v aplikácii pomocou ich ciest (routes).
// Používa sa na navigáciu medzi rôznymi obrazovkami.
// Každá vnorená objektová trieda reprezentuje jednu obrazovku s unikátnou cestou.
sealed class Screen(val rout:String) {
    object ReceptsScreen: Screen("receptsScreen")
    object Recept1: Screen("Recept1")
    object Uvod: Screen("Uvod")
    object ReceptScreenOblubene: Screen("Oblubene")
    object PridajRecept: Screen("Pridaj")

}