package com.mixonko.android.kotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.mixonko.android.kotlin.R
import com.mixonko.android.kotlin.entity.Note

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    private var notes: List<Note> = ArrayList()

    override fun getItemCount(): Int = notes.size

    fun setNotes(notes : List<Note>){
        this.notes = notes
        notifyDataSetChanged()
    }

    fun getNoteAt(position: Int): Note = notes.get(position)


    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        val currentNote: Note = notes.get(position)
        holder.bind(currentNote)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return ViewHolder(itemView)
    }

    inner class ViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView) {
        private val textViewTitle: TextView = itemView.findViewById(R.id.text_view_title)
        private val textViewDescription: TextView =
            itemView.findViewById(R.id.text_view_description)
        private val textViewPriority: TextView = itemView.findViewById(R.id.text_view_priority)

        fun bind(currentNote: Note) {
            textViewTitle.text = currentNote.title
            textViewDescription.text = currentNote.description
            textViewPriority.text = currentNote.priority.toString()

        }
    }
}
