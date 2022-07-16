package com.ahad.firebasekotlin.firestore

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahad.firebasekotlin.R
import kotlinx.android.synthetic.main.layout_product.view.*

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private val products:MutableList<Product> = mutableListOf()
    var productClickListener:ProductClickListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_product,parent,false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.itemView.text_view_name.text = product.name
        holder.itemView.text_view_brand.text = product.brand
        holder.itemView.text_view_desc.text = product.description
        holder.itemView.text_view_price.text = product.price.toString()
        holder.itemView.text_view_qnt.text = product.quantity.toString()
        holder.itemView.setOnClickListener {
            productClickListener?.let {
                productClickListener?.onProductClicked(Product.Action.ORDERS,products[position])
            }
        }
        holder.itemView.text_view_edit.setOnClickListener {
            productClickListener?.let {
                productClickListener?.onProductClicked(Product.Action.EDIT,products[position])
            }
        }
    }

    override fun getItemCount() :Int{
        return products.size
    }

    fun addProducts(products: MutableList<Product>) {
        this.products.clear()
        this.products.addAll(products)
        Log.d(TAG, "addProducts: size : ${products.size}")
        notifyDataSetChanged()
    }


    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface ProductClickListener {
        fun onProductClicked(action:Product.Action, product: Product)
    }

    companion object{
        private const val TAG = "TAG:ProductAdapter"
    }

}