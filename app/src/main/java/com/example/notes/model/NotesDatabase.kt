package com.example.notes.model

import androidx.room.Database
import androidx.room.RoomDatabase


@Database( entities = [NoteContent::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract val dao : NoteDao

}