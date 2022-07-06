package com.ahad.firebasekotlin.realtime

import com.google.firebase.database.Exclude

data class Author(@get:Exclude var id:String?, var name:String?,var city:String, var votes:Double){
    enum class Action {
        UPDATE,DELETE
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Author) {
            other.id.equals(this.id)
        } else false
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        return result
    }

    companion object{
        private const val TAG = "TAG:Author"
    }
}
