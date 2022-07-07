package com.ahad.firebasekotlin.realtime

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ahad.firebasekotlin.R
import kotlinx.android.synthetic.main.fragment_edit_author_dialog.*


class EditAuthorDialogFragment(val author: Author) : DialogFragment() {
    lateinit var viewModel: AuthorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_author_dialog, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as RealtimeActivity).viewModel

        viewModel.updating()
        edit_text_name.append(author.name)


        button_add.setOnClickListener {
            val name = edit_text_name.text.toString().trim()
            if (name.isEmpty()) {
                input_layout_name.error = getString(R.string.error_field_required)
                return@setOnClickListener
            }
            author.name = name
            viewModel.updateAuthor(author)
        }
        viewModel.updateResult.observe(viewLifecycleOwner, { response ->
            Toast.makeText(context, response, Toast.LENGTH_SHORT).show()
            if (response != AuthorViewModel.UPDATING)
                dismiss()
        })
    }

    companion object{
        private const val TAG = "TAG:Add"
    }
}