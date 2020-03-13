package com.mixonko.android.kotlin.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mixonko.android.kotlin.R
import com.mixonko.android.kotlin.adapter.NoteAdapter
import com.mixonko.android.kotlin.entity.Note
import com.mixonko.android.kotlin.viewModel.NoteViewModel

const val ADD_NOTE_REQUEST: Int = 1
const val EDIT_NOTE_REQUEST : Int = 2

class MainActivity : AppCompatActivity(), NoteAdapter.ItemClickListener  {
    override fun onItemClick(note: Note) {
        val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
        intent.putExtra(EXTRA_ID, note.id)
        intent.putExtra(EXTRA_TITLE, note.title)
        intent.putExtra(EXTRA_DESCRIPTION, note.description)
        intent.putExtra(EXTRA_PRIORITY, note.priority)

        startActivityForResult(intent, EDIT_NOTE_REQUEST)
    }

    override fun onItemLongClick(note: Note) {
        var builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Удалить ${note.title} ?")
            .setPositiveButton("Delete") { dialog, which ->
                noteViewModel.delete(note)
                Toast.makeText(this@MainActivity, "${note.title} was deleted", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.cancel()
            }.show()
    }

    lateinit var  noteViewModel: NoteViewModel
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
        noteAdapter.setItemClickListener(this)

        recyclerView.adapter = noteAdapter

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        noteViewModel.getAllNotes()?.observe(this, Observer <List<Note>>{
            noteAdapter.setNotes(it)
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK){

            val title = data!!.getStringExtra(EXTRA_TITLE)
            val description = data!!.getStringExtra(EXTRA_DESCRIPTION)
            val priority = data!!.getIntExtra(EXTRA_PRIORITY, 1)
            val note = Note(title = title, description = description, priority = priority)

            noteViewModel.incert(note)

            Toast.makeText(this, "Note saved", Toast.LENGTH_LONG).show()

        }else if (requestCode == EDIT_NOTE_REQUEST && resultCode == Activity.RESULT_OK){
            val title = data!!.getStringExtra(EXTRA_TITLE)
            val description = data!!.getStringExtra(EXTRA_DESCRIPTION)
            val priority = data!!.getIntExtra(EXTRA_PRIORITY, 1)
            val id = data!!.getIntExtra(EXTRA_ID, -1) 

            if (id == -1){
                return
            }
            var note = Note(title = title, description = description, priority = priority)
            note.id = id
            noteViewModel.update(note)
        }
    }
}
