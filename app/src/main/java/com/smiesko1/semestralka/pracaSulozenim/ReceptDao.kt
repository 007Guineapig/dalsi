package com.smiesko1.semestralka.pracaSulozenim

import androidx.compose.runtime.MutableState
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

    @Query("DELETE FROM receptik WHERE nazov = :nazov")
     fun deleteById(nazov: String)

    @Query("UPDATE receptik set kategoria =:kategoria WHERE nazov = :nazov")
    suspend fun update(kategoria:String, nazov: String)

    @Query("SELECT COUNT(*) FROM receptik WHERE nazov = :nazov AND kategoria = 'nejde'")
    suspend fun isClicked(nazov: String): Int


}











