package com.example.notes.di

import android.app.Application
import androidx.room.Room
import com.example.notes.model.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteModule {

    @Provides
    @Singleton
    fun provideDatabase(app : Application) : NotesDatabase{

        val database = Room.databaseBuilder(

            app,
            NotesDatabase::class.java,
            "notes_bd"
        ).build()
        return database
    }
}