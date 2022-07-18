package com.ahad.firebasekotlin.firestore

sealed class FireStoreResponse<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : FireStoreResponse<T>(data)

    class Error<T>(message: String, data: T? = null) : FireStoreResponse<T>(data, message)

    class Loading<T> : FireStoreResponse<T>()
}