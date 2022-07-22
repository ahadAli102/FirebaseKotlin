package com.ahad.firebasekotlin.firestore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.ahad.firebasekotlin.R
import kotlinx.android.synthetic.main.fragment_edit.*


class EditFragment : Fragment() {
    lateinit var viewModel: FireStoreViewModel
    val args: EditFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as FirestoreActivity).viewModel
        val product = args.passedProduct
        edittext_name.append(product.name)
        edittext_desc.append(product.description)
        edittext_brand.append(product.brand)
        edittext_price.append(product.price.toString())
        edittext_qty.append(product.quantity.toString())
        Toast.makeText(context, "Orders${product.name}", Toast.LENGTH_SHORT).show()

        button_update.setOnClickListener {
            val map = mutableMapOf<String,Any>()
            val name = edittext_name.text.toString().trim()
            val desc = edittext_desc.text.toString().trim()
            val brand = edittext_brand.text.toString().trim()
            val price = edittext_price.text.toString().trim().toFloat()
            val quant = edittext_qty.text.toString().trim().toInt()
            if(name != product.name){
                map["name"] = name
            }
            if(brand != product.brand){
                map["brand"] = brand
            }
            if(desc != product.description){
                map["description"] = desc
            }
            if(price != product.price){
                map["price"] = price
            }
            if(quant != product.quantity){
                map["quantity"] = quant
            }
            viewModel.updateProduct(map,product)
        }
        button_delete.setOnClickListener { viewModel.deleteProduct(product) }

        viewModel.updateResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is FireStoreResponse.Success -> {
                    response.data?.let {
                        Toast.makeText(context, "${response.data.name} updated successfully", Toast.LENGTH_SHORT).show()

                    }
                }

                is FireStoreResponse.Loading -> {
                    Toast.makeText(context, "${product.name} updating", Toast.LENGTH_SHORT).show()
                }
                is FireStoreResponse.Error ->{
                    Toast.makeText(context, "Error occur: ${response.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.deleteResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is FireStoreResponse.Success -> {
                    response.data?.let {
                        Toast.makeText(context, "${response.data.name} deleted successfully", Toast.LENGTH_SHORT).show()
                        (activity as FirestoreActivity).onBackPressed()
                    }
                }

                is FireStoreResponse.Loading -> {
                    Toast.makeText(context, "${product.name} deleting", Toast.LENGTH_SHORT).show()
                }
                is FireStoreResponse.Error ->{
                    Toast.makeText(context, "Error occur: ${response.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}