package com.example.roomdatabase.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.roomdatabase.database.NotesDatabase
import com.example.roomdatabase.model.NotesData

class AddViewModel: ViewModel()  {

    fun addData(context: Context, notesData: NotesData) {
        val notesDb = NotesDatabase.getDatabase(context)

        notesDb.notesDao().insertNotes(notesData)

    }

    fun updateData(context: Context, notesData: NotesData) {
        val notesDb = NotesDatabase.getDatabase(context)

        notesDb.notesDao().updateNotes(notesData)

    }
}