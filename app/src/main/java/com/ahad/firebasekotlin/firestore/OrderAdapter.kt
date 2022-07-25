package com.ahad.firebasekotlin.firestore

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahad.firebasekotlin.R
import kotlinx.android.synthetic.main.layout_order.view.*

class OrderAdapter : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    private val orders = mutableListOf<Order>()
    var orderClickListener:OrderClickListener? = null
    var orderProduct: Product?=null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_order,parent,false))
    }

    fun addOrders(orders:MutableList<Order>){
        this.orders.clear()
        this.orders.addAll(orders)
        this.notifyDataSetChanged()
        Log.d(TAG, "addOrders: size ${orders.size}")
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.itemView.order_location.text = order.location
        holder.itemView.order_amount.text = order.amount.toString()
        holder.itemView.order_buyer.text = order.name
        holder.itemView.order_name.text = orderProduct?.name
        holder.itemView.order_edit.setOnClickListener {
            orderClickListener?.let { orderClickListener!!.onOrderCLickListener(Order.Action.EDIT, orders[position]) }
        }
        holder.itemView.order_delete.setOnClickListener {
            orderClickListener?.let { orderClickListener!!.onOrderCLickListener(Order.Action.DELETE, orders[position]) }
        }
        Log.d(TAG, "onBindViewHolder: $order")
    }

    override fun getItemCount():Int{
        return orders.size
    }

    class OrderViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)

    interface OrderClickListener{
        fun onOrderCLickListener(action:Order.Action,order: Order)
    }

    companion object{
        private const val TAG = "TAG:OrderAdapter"
    }
}