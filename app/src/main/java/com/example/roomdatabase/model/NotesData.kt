package com.example.roomdatabase.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class NotesData( @PrimaryKey(autoGenerate = true)
                      var id : Int?,
                      var title : String,
                      var message : String)
