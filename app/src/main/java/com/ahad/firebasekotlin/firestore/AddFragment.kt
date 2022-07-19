package com.ahad.firebasekotlin.firestore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ahad.firebasekotlin.R
import kotlinx.android.synthetic.main.fragment_add.*


class AddFragment : Fragment() {
    lateinit var viewModel: FireStoreViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as FirestoreActivity).viewModel
        viewModel.resetInsertData()
        button_save.setOnClickListener {
            val product = Product("",
                edit_text_name.text.toString().trim(),
                edit_text_brand.text.toString().trim(),
                edit_text_desc.text.toString().trim(),
                edit_text_price.text.toString().trim().toFloat(),
                edit_text_qty.text.toString().trim().toInt(),
                System.currentTimeMillis()
            )
            viewModel.insertProduct(product)
        }

        viewModel.insertResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is FireStoreResponse.Success -> {
                    response.data?.let {
                        Toast.makeText(context, response.data, Toast.LENGTH_SHORT).show()
                    }
                }

                is FireStoreResponse.Loading -> {
                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                }
                is FireStoreResponse.Error ->{
                    Toast.makeText(context, "Error occur: ${response.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}