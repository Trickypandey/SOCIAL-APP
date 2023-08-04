package com.example.socialapp.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.socialapp.DataModel.ModelNewCatResponse
import com.example.socialapp.R
import com.example.socialapp.databinding.ActivityAddCategoryBinding
import com.example.socialapp.utils.Constant
import com.example.socialapp.utils.RetrofitHelper
import retrofit2.Call
import retrofit2.Response

class AddCategoryActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_category)
        binding.back.setOnClickListener {
            finish()
        }
        binding.btnPost.setOnClickListener {
            if (validateINput())
                apiCall()
        }
    }
    private fun validateINput(): Boolean {
        if (binding.ETTitle.text.isEmpty()){
            Toast.makeText(this@AddCategoryActivity,"TITLE Can't be Null", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.ETDescription.text.isEmpty()){
            Toast.makeText(this@AddCategoryActivity,"Description Can't be Null", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun apiCall() {
        val map = hashMapOf<String,String>()
        map["catgTitle"] = binding.ETTitle.text.toString()
        map["catgDesc"] = binding.ETDescription.text.toString()

        val addCat = RetrofitHelper().retrofitobj()
        addCat.addNewCat(Constant.token,map).enqueue(object : retrofit2.Callback<ModelNewCatResponse>{
            override fun onResponse(
                call: Call<ModelNewCatResponse>,
                response: Response<ModelNewCatResponse>
            ) {
                if (response.body()!!.status == "OK"){
                Toast.makeText(this@AddCategoryActivity, response.body()!!.message,Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this@AddCategoryActivity, response.body()!!.message,Toast.LENGTH_SHORT).show()
            }
                binding.ETTitle.text.clear()
                binding.ETDescription.text.clear()
            }

            override fun onFailure(call: Call<ModelNewCatResponse>, t: Throwable) {
                Toast.makeText(this@AddCategoryActivity, t.message,Toast.LENGTH_SHORT).show()
            }

        })

    }
}