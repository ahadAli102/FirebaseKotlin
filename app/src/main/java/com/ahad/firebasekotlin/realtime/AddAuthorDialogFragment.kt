package com.ahad.firebasekotlin.realtime

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ahad.firebasekotlin.R
import kotlinx.android.synthetic.main.fragment_add_author_dialog.*


class AddAuthorDialogFragment : DialogFragment() {
    private lateinit var viewModel: AuthorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_author_dialog, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as RealtimeActivity).viewModel
        viewModel.adding()
        viewModel.insertResult.observe(this, {
            val message = it
            if(it != AuthorViewModel.ADDING){
                dismiss()
            }
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        })

        button_add.setOnClickListener {
            val name = edit_text_name.text.toString().trim()
            val city = edit_text_city.text.toString().trim()
            val votes = edit_text_vote.text.toString().trim().toDouble()
            if (name.isEmpty()) {
                input_layout_name.error = getString(R.string.error_field_required)
                return@setOnClickListener
            }
            val author = Author(null,name,city,votes)
            viewModel.addAuthor(author)
        }
    }

    companion object{
        private const val TAG = "TAG:Add"
    }

}