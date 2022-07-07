package com.ahad.firebasekotlin.realtime

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ahad.firebasekotlin.R
import kotlinx.android.synthetic.main.fragment_author.*

class AuthorFragment : Fragment(),AuthorAdapter.MyAuthorClickListener {
    private val authorAdapter = AuthorAdapter()
    private lateinit var viewModel: AuthorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_author, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as RealtimeActivity).viewModel

        recycler_view_authors.adapter = authorAdapter
        authorAdapter.authorClickListener = this

        //load data
        viewModel.loadAuthors()
        viewModel.authors.observe(viewLifecycleOwner, { authors ->
            authorAdapter.addAuthors(authors)
            Log.d(TAG, "onViewCreated: ")
            //realtime add
            viewModel.realtimeUpdate()
        })


        viewModel.author.observe(viewLifecycleOwner,{ author->
            authorAdapter.addAuthors(author)
        })

        viewModel.deleteResult.observe(viewLifecycleOwner, { response->
            when(response) {
                is ResponseResource.Success -> {
                    Toast.makeText(context,"Delete successful", Toast.LENGTH_SHORT).show()
                    authorAdapter.deleteAuthor(response.data!!)
                }
                is ResponseResource.Error -> {
                    Log.e(TAG, "An error occurred: ${response.message!!}")
                }
                is ResponseResource.Loading -> {
                    Toast.makeText(context,"Deleting Data", Toast.LENGTH_SHORT).show()
                }
            }
        })


        button_add.setOnClickListener {
            AddAuthorDialogFragment().show(childFragmentManager, "")
        }
    }
    companion object {
        private const val TAG = "TAG:AuthorFragment"
    }

    override fun onAuthorItemClickListener(action: Author.Action, author: Author) {
        when(action){
            Author.Action.DELETE ->{
                viewModel.deleteAuthor(author)
            }
            Author.Action.UPDATE ->{
                EditAuthorDialogFragment(author).show(childFragmentManager,"")
            }
        }
    }
}