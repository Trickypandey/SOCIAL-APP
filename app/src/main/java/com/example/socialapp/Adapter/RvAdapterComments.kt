package com.example.socialapp.Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socialapp.DataModel.Comment
import com.example.socialapp.DataModel.CommentData

import com.example.socialapp.R

class RvAdapterComments(val list :ArrayList<Comment>, val activity : Activity): RecyclerView.Adapter<RvAdapterComments.ViewHolder>() {
    class ViewHolder(itemview: View):RecyclerView.ViewHolder(itemview) {
        val user = itemview.findViewById<TextView>(R.id.username)
//        val time = itemview.findViewById<TextView>()
        val comment = itemview.findViewById<TextView>(R.id.comment)
        val profile = itemview.findViewById<ImageView>(R.id.userprofile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.layout_comment,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.comment.text = list[position].content
        holder.user.text = list[position].user.name

    }
}