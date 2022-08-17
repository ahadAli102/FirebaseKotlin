package com.ahad.firebasekotlin.storage

import android.content.ContentResolver
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahad.firebasekotlin.R
import kotlinx.android.synthetic.main.item_file_storage.view.*

class AddFileAdapter(
    private var _uris: MutableList<Uri>,private val contentResolver: ContentResolver
): RecyclerView.Adapter<AddFileAdapter.ImageViewHolder>() {
    inner class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_file_storage,
                parent,
                false
            )
        )
    }

    fun setUris(uris: MutableList<Uri>){
        _uris = uris
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return _uris.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val uri = _uris[position]
        val fileName = FileUtil.getFileName(uri,contentResolver)
        if(fileName?.endsWith(".pdf") == true){
            holder.itemView.item_file_image.setImageResource(R.drawable.pdf)
        }else{
            holder.itemView.item_file_image.setImageResource(R.drawable.word)
        }
        holder.itemView.item_file_text.text = fileName
    }

    companion object{
        private const val TAG = "TAG:Storage:AddImageAd"
    }
}