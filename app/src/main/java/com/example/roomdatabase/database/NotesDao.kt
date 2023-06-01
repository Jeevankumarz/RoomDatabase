package com.example.roomdatabase.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.roomdatabase.model.NotesData
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Query("SELECT * FROM note_table ORDER BY id ASC")
    fun getAllNotes(): List<NotesData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNotes(notesData: NotesData)
}