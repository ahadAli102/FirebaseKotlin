package com.ahad.firebasekotlin.realtime

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahad.firebasekotlin.R
import com.ahad.firebasekotlin.realtime.Author
import kotlinx.android.synthetic.main.recycler_view_author.view.*

class AuthorAdapter : RecyclerView.Adapter<AuthorAdapter.AuthorViewHolder>() {

    private val authors:MutableList<Author> = ArrayList()
    var authorClickListener:MyAuthorClickListener? = null

    fun addAuthors(myAuthors:List<Author>){
        authors.addAll(myAuthors)
        notifyDataSetChanged()
    }

    fun addAuthors(myAuthor:Author){
        if(!authors.contains(myAuthor)){
            authors.add(myAuthor)
        }else{
            val position = authors.indexOf(myAuthor)
            authors[position] = myAuthor
        }
        notifyDataSetChanged()
    }

    fun deleteAuthor(author: Author){
        authors.remove(author)
        notifyDataSetChanged()
    }


    open class AuthorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorViewHolder {
        return AuthorViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_author,parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AuthorViewHolder, position: Int) {
        holder.itemView.text_view_name.text = authors[position].name
        holder.itemView.text_view_city_votes.text = "${authors[position].city} | ${authors[position].votes}"
        holder.itemView.button_edit.setOnClickListener{authorClickListener?.onAuthorItemClickListener(Author.Action.UPDATE,authors[position])}
        holder.itemView.button_delete.setOnClickListener{authorClickListener?.onAuthorItemClickListener(Author.Action.DELETE,authors[position])}
    }

    override fun getItemCount(): Int = authors.size

    interface MyAuthorClickListener{
        fun onAuthorItemClickListener(action:Author.Action, author: Author)
    }
    companion object{
        private const val TAG = "TAG:Adapter"
    }

}