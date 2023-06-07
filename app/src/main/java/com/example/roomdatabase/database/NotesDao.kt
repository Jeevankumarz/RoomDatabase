package com.example.roomdatabase.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.roomdatabase.model.NotesData

@Dao
interface NotesDao {
    @Query("SELECT * FROM note_table ORDER BY id ASC")
    fun getAllNotes(): List<NotesData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNotes(notesData: NotesData)
    @Delete
    fun deletenotes(notesData: ArrayList<NotesData>)
    @Update
    fun updateNotes(notesData: NotesData)
}