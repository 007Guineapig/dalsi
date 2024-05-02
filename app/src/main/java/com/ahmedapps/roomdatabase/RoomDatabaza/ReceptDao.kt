package com.ahmedapps.roomdatabase.RoomDatabaza

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface ReceptDao {

    @Upsert
    suspend fun upsertRecept(receptik: Receptik)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(receptik: Receptik)

    @Query("SELECT * FROM receptik")
    fun readAll():Flow<List<Receptik>>

    //@Delete
   // suspend fun deleteNote(receptik: Receptik)

    @Query("SELECT * FROM receptik ORDER BY nazov ASC")
    fun getNotesOrderdByTitle(): Flow<List<Receptik>>

}











