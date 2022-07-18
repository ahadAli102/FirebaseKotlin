package com.ahad.firebasekotlin.firestore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.*

class FireStoreViewModel : ViewModel() {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var productListener: ListenerRegistration


    private val _insertResponse = MutableLiveData<FireStoreResponse<String>>()
    val insertResponse: LiveData<FireStoreResponse<String>>
        get() = _insertResponse

    private val _updateResponse = MutableLiveData<FireStoreResponse<Product>>()
    val updateResponse: LiveData<FireStoreResponse<Product>>
        get() = _updateResponse

    private val _deleteResponse = MutableLiveData<FireStoreResponse<Product>>()
    val deleteResponse: LiveData<FireStoreResponse<Product>>
        get() = _deleteResponse

    private val _readResponse = MutableLiveData<FireStoreResponse<MutableList<Product>>>()
    val readResponse: LiveData<FireStoreResponse<MutableList<Product>>>
        get() = _readResponse


    private val _readOrderResponse = MutableLiveData<FireStoreResponse<MutableList<Order>>>()
    val readOrderResponse: LiveData<FireStoreResponse<MutableList<Order>>>
        get() = _readOrderResponse

    private val _insertOrderResponse = MutableLiveData<FireStoreResponse<Order>>()
    val insertOrderResponse: LiveData<FireStoreResponse<Order>>
        get() = _insertOrderResponse

    private val _updateOrderResponse = MutableLiveData<FireStoreResponse<Order>>()
    val updateOrderResponse: LiveData<FireStoreResponse<Order>>
        get() = _updateOrderResponse

    private val _deleteOrderResponse = MutableLiveData<FireStoreResponse<Order>>()
    val deleteOrderResponse: LiveData<FireStoreResponse<Order>>
        get() = _deleteOrderResponse


    init {
        readProduct()
    }

    fun insertProduct(product: Product){
        _insertResponse.value = FireStoreResponse.Loading()
        firestore.collection(PRODUCTS_KEY).document().set(product)
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    Log.d(TAG, "insertData: inserted")
                    _insertResponse.value = FireStoreResponse.Success(PRODUCT_INSERT_SUCCESSFUL)
                }
                else{
                    _insertResponse.value = FireStoreResponse.Error(task.exception!!.message!!)
                }
            }
    }

    fun resetInsertData(){
        _insertResponse.value = FireStoreResponse.Loading()
    }

    private fun readProduct(){
        Log.d(TAG, "readData: called")
        _readResponse.value = FireStoreResponse.Loading()
        var responseProducts: MutableList<Product>?
        productListener = firestore.collection(PRODUCTS_KEY).orderBy(TIME_KEY,Query.Direction.DESCENDING)
            .addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, error ->
                if (error != null){
                    _readResponse.value = FireStoreResponse.Error(error.message!!)
                }
                responseProducts = mutableListOf()
                if (!querySnapshot!!.isEmpty) {
                    for (documentSnapshot in querySnapshot.documents) {
                        val product = documentSnapshot.toObject(Product::class.java)
                        if (product != null) {
                            product.id = documentSnapshot.id
                            responseProducts?.add(product)
                            Log.d(TAG, "readData: ${product.name}-${product.time}")
                        }
                    }
                }
                responseProducts?.let{
                    Log.d(TAG, "readData: size ${responseProducts!!.size}")
                    _readResponse.value = FireStoreResponse.Success(responseProducts!!)
                }
            }
    }

    fun updateProduct(productMap: Map<String,Any>, product: Product){
        _updateResponse.value = FireStoreResponse.Loading()
        firestore.collection(PRODUCTS_KEY).document(product.id).set(productMap, SetOptions.merge())
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    _updateResponse.value = FireStoreResponse.Success(product)
                }
                else{
                    _updateResponse.value = FireStoreResponse.Error(task.exception!!.message!!)
                }
            }
    }

    fun deleteProduct(product: Product){
        _deleteResponse.value = FireStoreResponse.Loading()
        firestore.collection(PRODUCTS_KEY).document(product.id).delete()
            .addOnCompleteListener {  task->
                if(task.isSuccessful){
                    _deleteResponse.value = FireStoreResponse.Success(product)
                }
                else{
                    _deleteResponse.value = FireStoreResponse.Error(task.exception!!.message!!)
                }
            }
    }

    override fun onCleared() {
        super.onCleared()
        productListener.remove()
    }


    fun readOrder(product: Product){
        _readOrderResponse.value = FireStoreResponse.Loading()
        var responseOrders: MutableList<Order>?
        firestore.collection(ORDERS_KEY).orderBy(TIME_KEY,Query.Direction.DESCENDING)
            .whereEqualTo(ORDER_PRODUCT_KEY, product.id)
            .addSnapshotListener { querySnapshot, error ->
                if(error!=null){
                    _readOrderResponse.value = FireStoreResponse.Error(error.message!!)
                    return@addSnapshotListener
                }
                responseOrders = mutableListOf()
                if (!querySnapshot!!.isEmpty) {
                    for (documentSnapshot in querySnapshot.documents) {
                        val order = documentSnapshot.toObject(Order::class.java)
                        order?.id = documentSnapshot.id
                        if (order != null) {
                            responseOrders?.add(order)
                            Log.d(TAG, "readOrder: ${order.name}-${order.time}")
                        }
                    }
                }
                responseOrders?.let{
                    Log.d(TAG, "readOrder: size ${responseOrders!!.size}")
                    _readOrderResponse.value = FireStoreResponse.Success(responseOrders!!)
                }
            }
    }
    fun insertOrder( order: Order){
        _insertOrderResponse.value = FireStoreResponse.Loading()
        firestore.collection(ORDERS_KEY).document().set(order)
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    _insertOrderResponse.value = FireStoreResponse.Success(order)
                }
                else{
                    _insertOrderResponse.value = FireStoreResponse.Error(task.exception!!.message!!)
                }
            }
    }
    fun updateOrder(orderMap: Map<String,Any>, order: Order){
        _updateOrderResponse.value = FireStoreResponse.Loading()
        firestore.collection(ORDERS_KEY).document(order.id).set(orderMap, SetOptions.merge())
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    _updateOrderResponse.value = FireStoreResponse.Success(order)
                }
                else{
                    _updateOrderResponse.value = FireStoreResponse.Error(task.exception!!.message!!)
                }
            }
    }

    fun deleteOrder(order: Order){
        _deleteOrderResponse.value = FireStoreResponse.Loading()
        firestore.collection(ORDERS_KEY).document(order.id).delete()
            .addOnCompleteListener {  task->
                if(task.isSuccessful){
                    _deleteOrderResponse.value = FireStoreResponse.Success(order)
                }
                else{
                    _deleteOrderResponse.value = FireStoreResponse.Error(task.exception!!.message!!)
                }
            }
    }



    companion object{
        private const val PRODUCTS_KEY = "products"
        private const val ORDERS_KEY = "orders"
        private const val TIME_KEY = "time"
        private const val ORDER_PRODUCT_KEY = "productKey"

        private const val PRODUCT_INSERT_SUCCESSFUL = "Product inserted Successfully"
        private const val PRODUCT_INSERTING = "Insert new Product"

        private const val ORDER_INSERT_SUCCESSFUL = "Order places Successfully"
        private const val ORDER_INSERTING = "Place new order"

        private const val TAG = "TAG:FireVM"
    }
}