package com.mixonko.android.kotlin.dao

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mixonko.android.kotlin.entity.Note

interface NoteDao {

    @Insert
    fun insert (note: Note)

    @Update
    fun update (note: Note)

    @Delete
    fun delete (note: Note)

    @Query("DELETE FROM note_table")
    fun deleteAllNotes()

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    fun getAllNotes(): LiveData<List<Note>>
}
