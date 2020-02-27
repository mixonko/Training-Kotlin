package com.mixonko.android.kotlin.repository

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.mixonko.android.kotlin.dao.NoteDao
import com.mixonko.android.kotlin.database.NoteDataBase
import com.mixonko.android.kotlin.entity.Note

class NoteRepository(context: Context) {
    private var noteDao: NoteDao
    private var allNotes: LiveData<List<Note>>

    init {
        var database = NoteDataBase.invoke(context)
        noteDao = database.noteDao()
        allNotes = noteDao!!.getAllNotes()

    }

    fun getAllNotes(): LiveData<List<Note>> = allNotes

    fun update(note: Note) {
        UpdateNoteAsuncTask(noteDao).execute(note)
    }

    fun insert(note: Note) {
        InsertNoteAsuncTask(noteDao).execute(note)

    }

    fun delete(note: Note) {
        DeleteNoteAsuncTask(noteDao).execute(note)

    }

    fun deleteAllNotes() {
        DeleteAllNotesAsuncTask(noteDao).execute()

    }

    companion object {
        private class InsertNoteAsuncTask(var noteDao: NoteDao) : AsyncTask<Note, Void, Void>() {
            override fun doInBackground(vararg params: Note?): Void? {
                noteDao.insert(params.get(0)!!)
                return null
            }

        }

        private class UpdateNoteAsuncTask(var noteDao: NoteDao) : AsyncTask<Note, Void, Void>() {
            override fun doInBackground(vararg params: Note?): Void? {
                noteDao.update(params.get(0)!!)
                return null
            }

        }

        private class DeleteNoteAsuncTask(var noteDao: NoteDao) : AsyncTask<Note, Void, Void>() {
            override fun doInBackground(vararg params: Note?): Void? {
                noteDao.delete(params.get(0)!!)
                return null
            }

        }

        private class DeleteAllNotesAsuncTask(var noteDao: NoteDao) :
            AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void?): Void? {
                noteDao.deleteAllNotes()
                return null
            }

        }

    }

}

