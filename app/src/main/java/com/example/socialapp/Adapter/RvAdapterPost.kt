package com.example.socialapp.Adapter


import android.app.Activity
import android.content.Intent
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.socialapp.Activity.AddPostActivity
import com.example.socialapp.Activity.DetailPostScreenActivity
import com.example.socialapp.DataModel.ModelDeletePostResponse
import com.example.socialapp.DataModel.Post
import com.example.socialapp.Interfacces.OnPostItemClickListener

import com.example.socialapp.R
import com.example.socialapp.utils.Common
import com.example.socialapp.utils.Constant
import com.example.socialapp.utils.RetrofitHelper
import retrofit2.Call
import retrofit2.Response

class RvAdapterPost(val list :ArrayList<Post>,val activity :Activity):RecyclerView.Adapter<RvAdapterPost.ViewHolder>() {

    class ViewHolder(itemview: View):RecyclerView.ViewHolder(itemview) {
        val username = itemview.findViewById<TextView>(R.id.username)
        val description = itemview.findViewById<TextView>(R.id.description)
        val title = itemview.findViewById<TextView>(R.id.title)
        val time = itemview.findViewById<TextView>(R.id.post_time)
        val card = itemview.findViewById<ConstraintLayout>(R.id.allpostcard)
        val deletecard = itemview.findViewById<LinearLayout>(R.id.lldelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.layout_post,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        if (Constant.flag == 1){
            holder.deletecard.visibility = View.VISIBLE
        }
        else
            holder.deletecard.visibility = View.GONE
        holder.username.text=Html.fromHtml(list[position].user.name.toString(), Html.FROM_HTML_MODE_COMPACT)
        holder.description.text= Html.fromHtml(list[position].content.toString(), Html.FROM_HTML_MODE_COMPACT)
        holder.title.text= Html.fromHtml(list[position].title.toString(), Html.FROM_HTML_MODE_COMPACT)
        holder.time.text=Common().formatDate(list[position].lastModifiedDate)
        holder.card.setOnClickListener {
            val intent = Intent(activity.applicationContext, DetailPostScreenActivity::class.java)
            intent.putExtra("data",list[position])
            activity.startActivity(intent)
        }
        holder.deletecard.setOnClickListener{
            apiCall(list[position].id)
        }
    }

    private fun apiCall(id: Int) {
        val retrofit = RetrofitHelper().retrofitobj()
        retrofit.deletePost(Constant.token,id).enqueue(object : retrofit2.Callback<ModelDeletePostResponse>{
            override fun onResponse(
                call: Call<ModelDeletePostResponse>,
                response: Response<ModelDeletePostResponse>
            ) {
                Toast.makeText(activity, response.body()!!.message, Toast.LENGTH_SHORT).show()
            }
            override fun onFailure(call: Call<ModelDeletePostResponse>, t: Throwable) {
                Toast.makeText(activity,"${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }
}