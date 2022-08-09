package com.ahad.firebasekotlin.storage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.ahad.firebasekotlin.R
import kotlinx.android.synthetic.main.fragment_storage.*

class StorageFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_storage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        store_video.setOnClickListener {
            val action = StorageFragmentDirections.actionStorageFragmentToVideoFragment()
            Navigation.findNavController(it).navigate(action)
        }
        store_file.setOnClickListener {
            val action = StorageFragmentDirections.actionStorageFragmentToFileFragment()
            Navigation.findNavController(it).navigate(action)
        }
        store_image.setOnClickListener {
            val action = StorageFragmentDirections.actionStorageFragmentToImageFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    companion object {

    }
}