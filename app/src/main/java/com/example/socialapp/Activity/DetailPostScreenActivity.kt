package com.example.socialapp.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.socialapp.Adapter.RvAdapterComments
import com.example.socialapp.DataModel.Comment
import com.example.socialapp.DataModel.CommentResponse
import com.example.socialapp.DataModel.ModelCommentResponse
import com.example.socialapp.DataModel.Post
import com.example.socialapp.R
import com.example.socialapp.databinding.ActivityDetailPostScreenBinding
import com.example.socialapp.utils.Constant
import com.example.socialapp.utils.RetrofitHelper
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.POST

class DetailPostScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPostScreenBinding
    private lateinit var data: Post
    private val retrofit = RetrofitHelper().retrofitobj()
    private val list = ArrayList<Comment>()
    private lateinit var commentAdapter: RvAdapterComments

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_post_screen)
        data = intent.getSerializableExtra("data") as Post

        binding.username.text = data.user.name
        binding.postDate.text = data.lastModifiedDate
        binding.title.text = Html.fromHtml(data.title, Html.FROM_HTML_MODE_COMPACT)
        binding.description.text = Html.fromHtml(data.content, Html.FROM_HTML_MODE_COMPACT)
        Glide
            .with(this)
            .load(data.imageName)
            .centerCrop()
            .placeholder(R.drawable.ic_placeholder)
            .into(binding.postImage)


        allClick()

        if (savedInstanceState == null && list.isEmpty()) {
            apiCall()
        } else {
            setData()
        }
    }

    private fun apiCall() {
        retrofit.getComment(Constant.token, data.id).enqueue(object : retrofit2.Callback<CommentResponse> {
            override fun onResponse(call: Call<CommentResponse>, response: Response<CommentResponse>) {
                if (response.isSuccessful && response.body()?.statusCode == 200) {
                    val commentResponse = response.body()
                    commentResponse?.data?.comments?.let {
                        list.clear()
                        list.addAll(it)
                        setData()
                    }
                } else {
                    Toast.makeText(this@DetailPostScreenActivity, response.body()?.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                Toast.makeText(this@DetailPostScreenActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setData() {
        commentAdapter = RvAdapterComments(list, this@DetailPostScreenActivity)
        binding.rvComments.layoutManager = LinearLayoutManager(this@DetailPostScreenActivity)
        binding.rvComments.adapter = commentAdapter

        // Scroll to the latest comment (assuming the list is not empty)
        if (list.isNotEmpty()) {
            binding.rvComments.scrollToPosition(list.size - 1)
        }
    }

    private fun allClick() {
        binding.back.setOnClickListener {
            finish()
        }

        binding.btnsend.setOnClickListener {
            addComment()
        }
    }

    private fun addComment() {
        val requestBody = HashMap<String, String>()
        requestBody["content"] = binding.ETComment.text.toString()
        retrofit.addComment(Constant.token, Constant.userdata.data.login.userDetails.id, data.id, requestBody)
            .enqueue(object : retrofit2.Callback<CommentResponse> {
                override fun onResponse(
                    call: Call<CommentResponse>,
                    response: Response<CommentResponse>
                ) {
                    if (response.isSuccessful && response.body()?.statusCode == 200) {
                        // Comment added successfully, fetch the updated comments again
                        apiCall()
                        binding.ETComment.text.clear() // Clear the input field
                    } else {
                        Toast.makeText(
                            this@DetailPostScreenActivity,
                            response.body()?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                    Toast.makeText(this@DetailPostScreenActivity, t.message, Toast.LENGTH_SHORT).show()
                }
        })
    }

    // Save the comment list in case of configuration changes (e.g., screen rotation)
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("comment_list", list)
    }

    // Restore the comment list if available after configuration changes
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        list.clear()
        list.addAll(savedInstanceState.getParcelableArrayList("comment_list") ?: emptyList())
        setData()
    }
}