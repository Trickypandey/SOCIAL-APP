package com.example.socialapp.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socialapp.Adapter.RvAdapterPost
import com.example.socialapp.DataModel.ModelAllpostresponse
import com.example.socialapp.R
import com.example.socialapp.databinding.ActivityCategoryPostBinding
import com.example.socialapp.utils.Constant
import com.example.socialapp.utils.RetrofitHelper
import retrofit2.Call
import retrofit2.Response

class CategoryPostActivity : AppCompatActivity() {
    lateinit var binding : ActivityCategoryPostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_category_post)
        val  title = intent.getStringExtra("category")
        val id = intent.getIntExtra("id",0)
        binding.title.text = title
        binding.back.setOnClickListener {
            finish()
        }
        apicall(id)
    }

    private fun apicall(id :Int) {
        val allpost= RetrofitHelper().retrofitobj()
        allpost.getPostbyCategory(Constant.token, id).enqueue(object :retrofit2.Callback<ModelAllpostresponse>{
            override fun onResponse(call: Call<ModelAllpostresponse>, response: Response<ModelAllpostresponse>) {
//                Log.e("")
                if (response.body()!!.status=="OK"){
                    binding.postRv.layoutManager= LinearLayoutManager(this@CategoryPostActivity)
                    binding.postRv.adapter= RvAdapterPost(response.body()!!.data.posts,this@CategoryPostActivity)
                    Log.e("TAG","${response.body()!!.data}")
                }else{
                    Toast.makeText(this@CategoryPostActivity, response.body()!!.message, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ModelAllpostresponse>, t: Throwable) {
                Toast.makeText(this@CategoryPostActivity,"${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }
}