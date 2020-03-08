package com.mixonko.android.kotlin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mixonko.android.kotlin.entity.Note
import com.mixonko.android.kotlin.repository.NoteRepository

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private var noteRepository: NoteRepository? = null
    private var allNotes: LiveData<List<Note>>? = null

    init {
        noteRepository = NoteRepository(application)
        allNotes = noteRepository?.getAllNotes()
    }

    fun incert(note: Note){
        noteRepository?.insert(note)
    }

    fun update(note: Note){
        noteRepository?.update(note)
    }

    fun delete(note: Note){
        noteRepository?.delete(note)
    }

    fun deleteAllNotes(){
        noteRepository?.deleteAllNotes()
    }

    fun getAllNotes(): LiveData<List<Note>>? = allNotes

}
