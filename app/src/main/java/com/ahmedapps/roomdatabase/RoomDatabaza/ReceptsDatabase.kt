package com.ahmedapps.roomdatabase.RoomDatabaza

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [Receptik::class],
    version = 1
)
abstract class ReceptsDatabase: RoomDatabase(){
    abstract val dao: ReceptDao

}