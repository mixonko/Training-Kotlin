package com.mixonko.android.kotlin.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mixonko.android.kotlin.dao.NoteDao
import com.mixonko.android.kotlin.entity.Note

@Database(entities = arrayOf(Note::class), version = 1)
abstract class NoteDataBase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        private var INSTANCE: NoteDataBase? = null

        fun getDatabase(context: Context): NoteDataBase? {
            if (INSTANCE == null) {
                synchronized(NoteDataBase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        NoteDataBase::class.java, "note_data_base")
                        .addCallback(CALLBACK)
                        .build()
                }

            }

            return INSTANCE
        }

        private val CALLBACK = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateDataBase(INSTANCE!!).execute()
            }
        }

        private class populateDataBase(db: NoteDataBase) : AsyncTask<Void, Void, Void>() {
            val noteDao: NoteDao = db.noteDao()
            override fun doInBackground(vararg params: Void?): Void? {
                noteDao.insert(Note(title = "title 1", description = "description 1", priority = 1))
                noteDao.insert(Note(title ="title 2", description ="description 2", priority = 2))
                noteDao.insert(Note(title ="title 3", description ="description 3", priority = 3))
                return null
            }

        }

    }

}
