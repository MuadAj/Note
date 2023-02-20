package com.example.notes.ui

import android.icu.text.StringSearch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.model.NoteContent
import com.example.notes.model.NotesDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor( private val db : NotesDatabase) : ViewModel(){

    var notes by mutableStateOf(emptyList<NoteContent>())
    private set

    init{
        getNote()
    }
    fun getNote(){
        db.dao.getNotes().onEach { noteList->
            notes = noteList
        }.launchIn(viewModelScope)
    }

    fun search(search: String){
        if (search.isEmpty()){
            getNote()
        }
        notes=notes.filter { it.title.contains(search) }
    }
}