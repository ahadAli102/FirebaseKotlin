package com.ahad.firebasekotlin.realtime

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*

class AuthorViewModel : ViewModel() {
    private val dbAuthors = FirebaseDatabase
        .getInstance("https://fir-kotlin-efd0c-default-rtdb.asia-southeast1.firebasedatabase.app/")
        .reference.child("authors")

    private val _authors = MutableLiveData<MutableList<Author>>()
    val authors : LiveData<MutableList<Author>>
        get() = _authors

    private val _author = MutableLiveData<Author>()
    val author : LiveData<Author>
        get() = _author

    private val _insertResult = MutableLiveData<String>()
    val insertResult: LiveData<String>
        get() = _insertResult

    private val _updateResult = MutableLiveData<String>()
    val updateResult: LiveData<String>
        get() = _updateResult

    private val _deleteResult = MutableLiveData<ResponseResource<Author>>()
    val deleteResult: LiveData<ResponseResource<Author>>
        get() = _deleteResult


    fun addAuthor(author: Author) {
        val id = dbAuthors.push().key
        Log.d(TAG, "addAuthor: $id")
        val authorMap:MutableMap<String,Any> = HashMap()
        author.name?.let { name->
            authorMap["name"] =  name
            authorMap["city"] = author.city
            authorMap["votes"] = author.votes
            Log.d(TAG, "addAuthor: map $authorMap")
        }
        id?.let{ key->
            Log.d(TAG, "addAuthor: adding")
            dbAuthors.child(key).setValue(authorMap)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        _insertResult.value = "Added Successfully"
                        Log.d(TAG, "addAuthor: successful")
                    } else {
                        _insertResult.value = it.exception.toString()
                        Log.e(TAG, "addAuthor: failed", it.exception)
                    }
                }.addOnFailureListener { exception-> Log.e(TAG, "addAuthor: failed", exception) }
        }

    }

    fun adding() {
        _insertResult.value = ADDING
    }

    fun loadAuthors(){
        dbAuthors.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val responseAuthors: MutableList<Author> = ArrayList()
                for (authorSnapshot in snapshot.children) {
                    val name = authorSnapshot.child("name").value.toString()
                    val city = authorSnapshot.child("city").value.toString()
                    val votes = authorSnapshot.child("votes").value.toString().toDouble()
                    _author.value = Author(authorSnapshot.key,name,city,votes)
                }
                _authors.value = responseAuthors
                Log.d(TAG, "onDataChange: ${responseAuthors.size}")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "onCancelled: ", error.toException())
            }

        })
    }

    fun realtimeUpdate(){
        dbAuthors.addChildEventListener(realtimeChildEventListener)
    }

    private val realtimeChildEventListener = object : ChildEventListener {
        override fun onChildAdded(authorSnapshot: DataSnapshot, previousChildName: String?) {
            val name = authorSnapshot.child("name").value.toString()
            val city = authorSnapshot.child("city").value.toString()
            val votes = authorSnapshot.child("votes").value.toString().toDouble()
            _author.value = Author(authorSnapshot.key,name,city,votes)
        }

        override fun onChildChanged(authorSnapshot: DataSnapshot, previousChildName: String?) {
            val name = authorSnapshot.child("name").value.toString()
            val city = authorSnapshot.child("city").value.toString()
            val votes = authorSnapshot.child("votes").value.toString().toDouble()
            _author.value = Author(authorSnapshot.key,name,city,votes)
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {

        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

        }

        override fun onCancelled(error: DatabaseError) {

        }
    }

    fun updating() {
        _insertResult.value = UPDATING
    }

    fun updateAuthor(author: Author) {
        val id = author.id
        val authorMap:MutableMap<String,Any> = HashMap()
        author.name?.let { name->
            authorMap["name"] =  name
            authorMap["city"] =  author.city
            authorMap["votes"] =  author.votes
        }
        id?.let{ key->
            dbAuthors.child(key).setValue(authorMap)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        _updateResult.value = UPDATED_SUCCESSFULLY
                    } else {
                        _updateResult.value = it.exception.toString()
                        Log.e(TAG, "addAuthor: failed", it.exception)
                    }
                }.addOnFailureListener { exception-> Log.e(TAG, "addAuthor: failed", exception) }
        }

    }



    override fun onCleared() {
        super.onCleared()
        dbAuthors.removeEventListener(realtimeChildEventListener)
    }

    fun deleteAuthor(author: Author) {
        _deleteResult.value = ResponseResource.Loading()
        dbAuthors.child(author.id!!).removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _deleteResult.value = ResponseResource.Success(author)
                } else {
                    _deleteResult.value = ResponseResource.Error("Loading Failed")
                    Log.e(TAG, "addAuthor: failed", it.exception)
                }
            }.addOnFailureListener { exception-> Log.e(TAG, "addAuthor: failed", exception) }
    }

    fun fetchFilteredAuthors(index: Int) {
        val dbAuthors =
            when (index) {
                1 ->
                    //#1 SELECT * FROM Authors
                    FirebaseDatabase.getInstance().getReference("authors")

                2 ->
                    //#2 SELECT * FROM Authors WHERE id = ?
                    FirebaseDatabase.getInstance().getReference("authors")
                        .child("-M-3fFw3GbovXWguSjp8")

                3 ->
                    //#3 SELECT * FROM Authors WHERE city = ?
                    FirebaseDatabase.getInstance().getReference("authors")
                        .orderByChild("city")
                        .equalTo("Hyderabad")

                4 ->
                    //#4 SELECT * FROM Authors LIMIT 2
                    FirebaseDatabase.getInstance().getReference("authors")
                        .limitToFirst(2)

                5 ->
                    //#5 SELECT * FROM Authors WHERE votes < 500
                    FirebaseDatabase.getInstance().getReference("authors")
                        .orderByChild("votes")
                        .endAt(500.toDouble())

                6 ->
                    //#6 SELECT * FROM Artists WHERE name LIKE "A%"
                    FirebaseDatabase.getInstance().getReference("authors")
                        .orderByChild("name")
                        .startAt("A")
                        .endAt("A\uf8ff")

                7 ->
                    //#7 SELECT * FROM Artists Where votes < 500 AND city = Bangalore
                    FirebaseDatabase.getInstance().getReference("authors")
                else -> FirebaseDatabase.getInstance().getReference("authors")
            }

        dbAuthors.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val authors = mutableListOf<Author>()
                    for (authorSnapshot in snapshot.children) {
                        val author = authorSnapshot.getValue(Author::class.java)
                        author?.id = authorSnapshot.key
                        author?.let { authors.add(it) }
                    }
                    _authors.value = authors
                }
            }
        })
    }


    companion object{
        private const val TAG = "TAG:ViewModel"
        const val ADDING = "Adding Data"
        const val UPDATING = "Updating Data"
        const val DELETING = "Deleting Data"
        const val GETTING = "Getting Data"

        const val UPDATED_SUCCESSFULLY = "Update Successfully"
    }
}