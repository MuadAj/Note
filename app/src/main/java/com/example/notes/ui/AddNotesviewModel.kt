package com.example.notes.ui

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.model.NoteContent
import com.example.notes.model.NotesDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNotesviewModel @Inject constructor( private val db : NotesDatabase) : ViewModel(){

    fun addNotes(value: TextFieldValue, value2: TextFieldValue) {
        val nConten=NoteContent(title = value.text, note = value2.text)
        viewModelScope.launch {
            db.dao.addNote(nConten)
        }
    }

    fun editNote(edit : NoteContent){
        viewModelScope.launch {
            db.dao.deleteNote(edit)
        }
    }

    fun deleteNote(NContent : NoteContent) {
        viewModelScope.launch {
            db.dao.deleteNote(NContent)
        }
    }

}