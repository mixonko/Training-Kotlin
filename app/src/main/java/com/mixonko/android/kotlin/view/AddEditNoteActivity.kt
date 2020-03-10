package com.mixonko.android.kotlin.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mixonko.android.kotlin.R

val EXTRA_ID = "com.mixonko.android.kotlin.view.EXTRA_ID"
val EXTRA_TITLE = "com.mixonko.android.kotlin.view.EXTRA_TITLE"
val EXTRA_DESCRIPTION = "com.mixonko.android.kotlin.view.EXTRA_DESCRIPTION"
val EXTRA_PRIORITY = "com.mixonko.android.kotlin.view.EXTRA_PRIORITY"

class AddEditNoteActivity: AppCompatActivity() {

    lateinit var descriptionEditText: EditText
    lateinit var priorityEditText: EditText
    lateinit var titleEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        descriptionEditText = findViewById (R.id.edit_text_description)
        priorityEditText = findViewById (R.id.edit_text_priority)
        titleEditText = findViewById(R.id.edit_text_title)

        getSupportActionBar()?.setHomeAsUpIndicator(R.drawable.ic_close)

        val intent = getIntent()

        if (intent.hasExtra(EXTRA_ID)){
            setTitle(R.string.edit_note)
            descriptionEditText.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
            priorityEditText.setText(intent.getIntExtra(EXTRA_PRIORITY, 0).toString())
            titleEditText.setText(intent.getStringExtra(EXTRA_TITLE))
        }else setTitle(R.string.save_note)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.add_edit_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId){
            R.id.done -> saveNote()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun saveNote() {

        val title = titleEditText.getText().toString()
        val description = descriptionEditText.getText().toString()
        val priority = priorityEditText.getText().toString().toInt()

        if (title.trim().isEmpty() ||
            description.trim().isEmpty()||
            priority.toString().trim().isEmpty()){
            Toast.makeText(this, "Please inset a title and description", Toast.LENGTH_LONG).show()
            return
        }else{
            val intent = Intent()
            intent.putExtra(EXTRA_TITLE, title)
            intent.putExtra(EXTRA_DESCRIPTION, description)
            intent.putExtra(EXTRA_PRIORITY, priority)

            val id = getIntent().getIntExtra(EXTRA_ID, -1)
            if (id != -1){
                intent.putExtra(EXTRA_ID, id)
            }

            setResult(Activity.RESULT_OK, intent)
            finish()
        }


    }
}
