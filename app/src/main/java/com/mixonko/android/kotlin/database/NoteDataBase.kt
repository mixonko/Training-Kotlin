package com.mixonko.android.kotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mixonko.android.kotlin.dao.NoteDao
import com.mixonko.android.kotlin.entity.Note

@Database(entities = arrayOf(Note::class), version = 1)
abstract class NoteDataBase: RoomDatabase() {
    abstract fun noteDao():NoteDao

    companion object {
        @Volatile private var instance: NoteDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            NoteDataBase::class.java, "todo-list.db")
            .build()
    }

}
