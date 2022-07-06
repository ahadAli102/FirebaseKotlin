package com.ahad.firebasekotlin.realtime

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahad.firebasekotlin.R
import com.example.firebaseauth.realtimedatabase.Author
import kotlinx.android.synthetic.main.fragment_add_author_dialog.*


class AddAuthorDialogFragment : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_author_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_add.setOnClickListener {
            val name = edit_text_name.text.toString().trim()
            val city = edit_text_city.text.toString().trim()
            val votes = edit_text_vote.text.toString().trim().toDouble()
            if (name.isEmpty()) {
                input_layout_name.error = getString(R.string.error_field_required)
                return@setOnClickListener
            }
            val author = Author(null,name,city,votes)
        }
    }

}