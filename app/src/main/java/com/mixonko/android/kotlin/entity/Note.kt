package com.mixonko.android.kotlin.entity

import androidx.room.PrimaryKey

class Note (_title: String, _description:String, _priority:Int) {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
        set(value){
            field = value
        }
        get(){
            return field
        }

    var title = _title
        set(value){
            field = value
        }
        get(){
            return field
        }
    var description = _description
        set(value){
            field = value
        }
        get(){
            return field
        }
    var priority = _priority
        set(value){
            field = value
        }
        get(){
            return field
        }
}
