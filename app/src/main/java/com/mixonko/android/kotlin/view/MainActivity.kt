package com.mixonko.android.kotlin.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mixonko.android.kotlin.R
import com.mixonko.android.kotlin.adapter.NoteAdapter
import com.mixonko.android.kotlin.entity.Note
import com.mixonko.android.kotlin.viewModel.AddEditNoteActivity
import com.mixonko.android.kotlin.viewModel.NoteViewModel
import kotlinx.android.synthetic.main.activity_main.*

val ADD_NOTE_REQUEST: Int = 1
val EDIT_NOTE_REQUEST : Int = 2

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonAddNote: FloatingActionButton = findViewById(R.id.button_add_note)
        buttonAddNote.setOnClickListener{
            val intent  = Intent(this, AddEditNoteActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST)
        }

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        val noteAdapter = NoteAdapter()
        recyclerView.adapter = noteAdapter

        val noteViewModel: NoteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        noteViewModel.getAllNotes()?.observe(this, Observer <List<Note>>{
            noteAdapter.setNotes(it)
        })


    }
}
