package com.ahad.firebasekotlin.firestore

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.ahad.firebasekotlin.R
import kotlinx.android.synthetic.main.fragment_details.*


class DetailsFragment : Fragment(),OrderAdapter.OrderClickListener {
    private val adapter = OrderAdapter()
    lateinit var viewModel: FireStoreViewModel
    private val args: DetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val product = args.passedProduct
        recyclerview_orders.adapter = adapter
        adapter.orderClickListener = this
        adapter.orderProduct = product
        viewModel = (activity as FirestoreActivity).viewModel
        viewModel.readOrderResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is FireStoreResponse.Success -> {
                    response.data?.let {
                        Log.d(TAG, "onViewCreated: size ${response.data.size}")
                        adapter.addOrders(response.data)
                        progressbar.visibility = View.GONE
                    }
                }
                is FireStoreResponse.Error -> {
                    Toast.makeText(context, "Error occur: ${response.message}", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "onViewCreated: response error ${response.message}")
                    progressbar.visibility = View.GONE
                }
                is FireStoreResponse.Loading -> {
                    Toast.makeText(context, "Reading Data", Toast.LENGTH_SHORT).show()
                }

            }
        })

        viewModel.deleteOrderResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is FireStoreResponse.Success -> {
                    response.data?.let {
                        Toast.makeText(context, "Order deleted", Toast.LENGTH_SHORT).show()
                    }
                }
                is FireStoreResponse.Error -> {
                    Toast.makeText(context, "Error occur: ${response.message}", Toast.LENGTH_SHORT)
                        .show()
                }
                is FireStoreResponse.Loading -> {
                    Toast.makeText(context, "Deleting Order", Toast.LENGTH_SHORT).show()
                }

            }
        })
        viewModel.readOrder(product)

        homeAddButtonId.setOnClickListener {
            AddOrderDialogFragment(product).show(childFragmentManager,"")
        }

    }

    companion object{
        private const val TAG = "TAG:Details"
    }

    override fun onOrderCLickListener(action: Order.Action, order: Order) {
        when(action){
            Order.Action.EDIT -> {
                EditOrderDialogFragment(order).show(childFragmentManager,"")
                Toast.makeText(context, "${order.name} will edit", Toast.LENGTH_SHORT).show()
            }
            Order.Action.DELETE -> {
                Toast.makeText(context, "${order.name} will delete", Toast.LENGTH_SHORT).show()
                viewModel.deleteOrder(order)
            }
        }
    }
}