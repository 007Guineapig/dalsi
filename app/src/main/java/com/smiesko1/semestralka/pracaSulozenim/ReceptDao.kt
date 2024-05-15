package com.smiesko1.semestralka.pracaSulozenim

import androidx.compose.runtime.MutableState
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

//tato trieda definuje operácie pre prácu s databázou receptov
@Dao
interface ReceptDao {

    //Vlozenie Receptu do ROOM databazy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(receptik: Receptik)

    //Vypytanie Receptov z ROOM databazy
    @Query("SELECT * FROM receptik")
    fun readAll():Flow<List<Receptik>>

    //Vypytanie Receptov z ROOM databazy podla abeecedy
    @Query("SELECT * FROM receptik ORDER BY nazov ASC")
    fun getReceptsOrderdByTitle(): Flow<List<Receptik>>

    //Vypytanie Receptov množstvo
    @Query("SELECT COUNT(*) FROM receptik")
    fun getCount(): Flow<Int>
    //Odstranenie Receptu do ROOM databazy
    @Query("DELETE FROM receptik WHERE nazov = :nazov")
     fun deleteById(nazov: String)
    //Aktualizovanie Receptu do ROOM databazy
    @Query("UPDATE receptik set kategoria =:kategoria WHERE nazov = :nazov")
    suspend fun update(kategoria:String, nazov: String)
    //Vypitanie Receptu do či je medzi oblubenymi
    @Query("SELECT COUNT(*) FROM receptik WHERE nazov = :nazov AND kategoria = 'oblubene'")
    suspend fun isClicked(nazov: String): Int



}











