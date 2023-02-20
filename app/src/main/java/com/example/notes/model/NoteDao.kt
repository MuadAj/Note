package com.example.notes.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {

    @Insert
    suspend fun addNote(note : NoteContent)

    @Delete
    suspend fun deleteNote(note : NoteContent)

    @Update
    suspend fun editNote(note : NoteContent)

    @Query("select * from NoteContent")
    fun getNotes(): Flow<List<NoteContent>>

}