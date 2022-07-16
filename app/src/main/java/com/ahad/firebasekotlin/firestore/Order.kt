package com.ahad.firebasekotlin.firestore

import com.google.firebase.firestore.Exclude

data class Order (@Exclude var id:String="", var name:String="",
                  var location:String="", var amount:Int=0,
                  var time:Long=0,var productKey:String = "") {
    enum class Action {
        EDIT,
        DELETE
    }

}
