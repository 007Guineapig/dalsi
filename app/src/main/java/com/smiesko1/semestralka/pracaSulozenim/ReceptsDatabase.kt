package com.smiesko1.semestralka.pracaSulozenim

import androidx.room.Database
import androidx.room.RoomDatabase

//Definovanie ROOM databazy
@Database(
    entities = [Receptik::class],
    version = 1
)
abstract class ReceptsDatabase: RoomDatabase(){
    abstract val dao: ReceptDao

}