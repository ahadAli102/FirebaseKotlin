package com.ahad.firebasekotlin.firestore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.ahad.firebasekotlin.R
import kotlinx.android.synthetic.main.fragment_add_order_dialog.*


class AddOrderDialogFragment(product: Product) : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_order_dialog, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        button_add_order.setOnClickListener {
            val name = edit_text_order_name.text.toString().trim()
            val location = edit_text_order_location.text.toString().trim()
            val amount = edit_text_order_amount.text.toString().trim().toInt()
            if(name.isEmpty()){
                edit_text_order_name.requestFocus()
                Toast.makeText(context, "Enter name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(location.isEmpty()){
                edit_text_order_location.requestFocus()
                Toast.makeText(context, "Enter location", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(amount==0){
                edit_text_order_amount.requestFocus()
                Toast.makeText(context, "Enter amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //product's order will be added
        }
    }

    companion object {

    }
}