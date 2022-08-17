package com.ahad.firebasekotlin.storage

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahad.firebasekotlin.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_image_storage.view.*

class AddImageAdapter(
    private var _uris: MutableList<Uri>
): RecyclerView.Adapter<AddImageAdapter.ImageViewHolder>() {
    inner class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_image_storage,
                parent,
                false
            )
        )
    }

    fun setUris(uris: MutableList<Uri>){
        Log.d(TAG, "setUris: come ${uris.size}")
        _uris = uris
        Log.d(TAG, "setUris: ${uris.size}")
        Log.d(TAG, "setUris: ad ${_uris.size}")
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return _uris.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val url = _uris[position]
        Glide.with(holder.itemView).load(url).into(holder.itemView.ivImage)
    }

    companion object{
        private const val TAG = "TAG:Storage:AddImageAd"
    }
}