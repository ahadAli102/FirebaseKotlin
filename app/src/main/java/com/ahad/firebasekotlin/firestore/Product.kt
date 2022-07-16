package com.ahad.firebasekotlin.firestore

import com.google.firebase.firestore.Exclude
import java.io.Serializable

data class Product(@Exclude var id:String = "", var name:String="",
                   var brand:String="", var description:String="",
                   var price:Float=0f, var quantity:Int=0,
                    var time:Long=0):Serializable{
    enum class Action{
        EDIT,
        ORDERS
    }
}
