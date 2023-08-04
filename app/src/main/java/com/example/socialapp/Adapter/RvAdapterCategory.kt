package com.example.socialapp.Adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.socialapp.Activity.CategoryPostActivity
import com.example.socialapp.DataModel.ListCategory
import com.example.socialapp.DataModel.Post
import com.example.socialapp.DataModel.ResponseCategory
import com.example.socialapp.R

class RvAdapterCategory(val list :ArrayList<ListCategory>, val activity : Activity): RecyclerView.Adapter<RvAdapterCategory.ViewHolder>() {
    class ViewHolder(itemview: View):RecyclerView.ViewHolder(itemview) {
        val title = itemview.findViewById<TextView>(R.id.tv_ct_title)
        val desc = itemview.findViewById<TextView>(R.id.tv_ct_desc)
        val allcard = itemview.findViewById<CardView>(R.id.allcard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_categories,parent,false)
        return RvAdapterCategory.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.desc.text=list[position].catgDesc
        holder.title.text=list[position].catgTitle
        holder.allcard.setOnClickListener {
            val intent = Intent(activity,CategoryPostActivity::class.java)
            intent.putExtra("id",list[position].id)
            intent.putExtra("category",list[position].catgTitle)
            activity.startActivity(intent)
        }
    }


}