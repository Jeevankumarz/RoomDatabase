package com.example.roomdatabase.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.roomdatabase.database.NotesDatabase
import com.example.roomdatabase.model.NotesData
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MainViewModel : ViewModel() {
    val applicationScope = CoroutineScope(SupervisorJob())
    var model: MutableLiveData<ArrayList<NotesData>> = MutableLiveData()

    fun getNotesData(context: Context) {
        val notesDb = NotesDatabase.getDatabase(context)

        val list = notesDb.notesDao().getAllNotes()
        if (!list.isNullOrEmpty()) {
            model.postValue(list as ArrayList)
        }

        Log.d("list", "getNotesData: ${Gson().toJson(list)} ")

    }


}