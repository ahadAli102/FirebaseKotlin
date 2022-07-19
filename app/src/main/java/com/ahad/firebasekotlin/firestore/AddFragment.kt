package com.ahad.firebasekotlin.firestore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahad.firebasekotlin.R
import kotlinx.android.synthetic.main.fragment_add.*


class AddFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_save.setOnClickListener {
            val product = Product("",
                edit_text_name.text.toString().trim(),
                edit_text_brand.text.toString().trim(),
                edit_text_desc.text.toString().trim(),
                edit_text_price.text.toString().trim().toFloat(),
                edit_text_qty.text.toString().trim().toInt(),
                System.currentTimeMillis()
            )
            //product will be added
        }


    }
}