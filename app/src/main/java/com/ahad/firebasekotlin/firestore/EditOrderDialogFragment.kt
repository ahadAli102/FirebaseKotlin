package com.ahad.firebasekotlin.firestore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.ahad.firebasekotlin.R
import kotlinx.android.synthetic.main.fragment_edit_order_dialog.*


class EditOrderDialogFragment(private val order:Order) : DialogFragment() {
    lateinit var viewModel: FireStoreViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_order_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as FirestoreActivity).viewModel
        viewModel.updateOrderResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is FireStoreResponse.Success -> {
                    response.data?.let {
                        Toast.makeText(context, "Order updated", Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                }
                is FireStoreResponse.Error -> {
                    Toast.makeText(context, "Error occur: ${response.message}", Toast.LENGTH_SHORT).show()
                }
                is FireStoreResponse.Loading -> {
                    Toast.makeText(context, "Updating Order", Toast.LENGTH_SHORT).show()
                }
            }
        })

        button_add_order.setOnClickListener {
            val orderMap = mutableMapOf<String,Any>()
            val name = edit_text_order_name.text.toString().trim()
            val location = edit_text_order_location.text.toString().trim()
            val amount = edit_text_order_amount.text.toString().trim().toInt()
            if(name.isNotEmpty() && name!= order.name){
                edit_text_order_name.requestFocus()
                orderMap["name"] = name
            }
            if(location.isNotEmpty() && name!= order.location){
                edit_text_order_location.requestFocus()
                orderMap["location"] = location
            }
            if(amount==0 && amount!= order.amount){
                edit_text_order_amount.requestFocus()
                orderMap["amount"] = amount
            }
            viewModel.updateOrder(orderMap,order)
        }
    }
}