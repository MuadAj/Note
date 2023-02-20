package com.example.notes.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class NoteContent(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    var title : String,
    var note : String
) : Parcelable
