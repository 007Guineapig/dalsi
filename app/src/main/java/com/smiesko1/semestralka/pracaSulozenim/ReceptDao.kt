package com.smiesko1.semestralka.pracaSulozenim

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
    fun insert(receptik: Receptik)

    @Query("SELECT * FROM receptik")
    fun readAll():Flow<List<Receptik>>

    @Query("SELECT * FROM receptik ORDER BY nazov ASC")
    fun getReceptsOrderdByTitle(): Flow<List<Receptik>>

    @Query("SELECT COUNT(*) FROM receptik")
    fun getCount(): Flow<Int>

}











