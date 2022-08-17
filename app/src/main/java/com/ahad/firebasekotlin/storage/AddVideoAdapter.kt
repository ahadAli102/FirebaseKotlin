package com.ahad.firebasekotlin.storage

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahad.firebasekotlin.R
import kotlinx.android.synthetic.main.item_video_storage.view.*
import kotlinx.android.synthetic.main.item_video_storage.view.ivVideo

class AddVideoAdapter(
    private var _uris: MutableList<Uri>,private val context:Context
): RecyclerView.Adapter<AddVideoAdapter.ImageViewHolder>() {
    inner class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_video_storage,
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
        val uri = _uris[position]
        /*holder.itemView.ivVideo.setVideoURI(uri)
        holder.itemView.ivVideo.start()
        holder.itemView.ivVideo.pause()
        holder.itemView.ivPause.setOnClickListener { holder.itemView.ivVideo.pause() }
        holder.itemView.ivResume.setOnClickListener { holder.itemView.ivVideo.resume() }*/

        try{
            val videoView = holder.itemView.ivVideo
//            val mediaController = MediaController(context)
//            mediaController.setAnchorView(videoView)
//            videoView.setMediaController(mediaController)
            videoView.setVideoURI(uri)
            videoView.requestFocus()
            videoView.start()
            holder.itemView.ivPause.setOnClickListener { videoView.pause() }
            holder.itemView.ivResume.setOnClickListener { videoView.resume() }
        }catch(e:Exception){

        }

    }

    companion object{
        private const val TAG = "TAG:Storage:AddImageAd"
    }
}