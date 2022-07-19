package com.ahad.firebasekotlin.firestore

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.ahad.firebasekotlin.R
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), ProductAdapter.ProductClickListener {
    private val adapter = ProductAdapter()
    lateinit var viewModel: FireStoreViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerview_products.adapter = adapter
        adapter.productClickListener = this
//        val products = mutableListOf<Product>()
//        products.add(
//            Product("123456","Sony Xperia Mark1",
//            "Sony","60Mp 4k camera, Snapdragon 888 SoC,4K LED display",1500.00f,5),
//        )
//        adapter.addProducts(products)

        add_product.setOnClickListener { view->
            val action = HomeFragmentDirections.actionHomeFragmentToAddFragment()
            Navigation.findNavController(view).navigate(action)
        }
        viewModel = (activity as FirestoreActivity).viewModel

        viewModel.readResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is FireStoreResponse.Success -> {
                    response.data?.let {
                        Log.d(TAG, "onViewCreated: size ${response.data.size}")
                        adapter.addProducts(response.data)
                    }
                }
                is FireStoreResponse.Error -> {
                    Toast.makeText(context, "Error occur: ${response.message}", Toast.LENGTH_SHORT)
                        .show()
                }
                is FireStoreResponse.Loading -> {
                    Toast.makeText(context, "Reading Data", Toast.LENGTH_SHORT).show()
                }

            }
        })
    }

    override fun onProductClicked(action: Product.Action, product: Product) {
        when(action){
            Product.Action.ORDERS -> {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(product)
                view?.let { Navigation.findNavController(it).navigate(action) }
            }
            Product.Action.EDIT ->{
                val action = HomeFragmentDirections.actionHomeFragmentToEditFragment(product)
                view?.let { Navigation.findNavController(it).navigate(action) }
            }
        }
    }

    companion object{
        private const val TAG = "TAG:Home"
    }
}