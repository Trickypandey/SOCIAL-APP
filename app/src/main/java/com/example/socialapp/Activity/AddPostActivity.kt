package com.example.socialapp.Activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.socialapp.DataModel.ModelAllpostresponse
import com.example.socialapp.DataModel.ResponseCategory
import com.example.socialapp.R
import com.example.socialapp.databinding.ActivityAddPostBinding
import com.example.socialapp.utils.Constant
import com.example.socialapp.utils.RetrofitHelper
import retrofit2.Call
import retrofit2.Response


class AddPostActivity : AppCompatActivity() {
    lateinit var binding:ActivityAddPostBinding
    val REQUEST_IMAGE_PICK = 1
    val REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_add_post)
        setSpinner()
        allCLick()
    }

    private fun allCLick() {
        binding.btnPost.setOnClickListener {
            if (validateINput())
                apiCall()
//            val selectedItem= binding.spCategory.selectedItem.toString()
//            Log.e("TAG","${selectedItem}")
        }
        binding.back.setOnClickListener {
            finish()
        }

        binding.imgView.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_PERMISSION_READ_EXTERNAL_STORAGE
                )
            } else {
                openImagePicker()
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
            try {
                // Get the selected image data and display it in the ImageView
                val selectedImageBitmap =
                    MediaStore.Images.Media.getBitmap(this.contentResolver, data?.data )
                Glide
                    .with(this)
                    .load(selectedImageBitmap)
                    .centerCrop()
                    .placeholder(R.drawable.ic_placeholder)
                    .into(binding.imgView);

                binding.imgView.setImageBitmap(selectedImageBitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_READ_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, open image picker
                openImagePicker()
            } else {
                // Permission denied, show a message or handle the case where the user denied permission
                Toast.makeText(this, "Permission denied. Cannot access images.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    private fun validateINput(): Boolean {
        if (binding.ETTitle.text.isEmpty()){
            Toast.makeText(this@AddPostActivity,"TITLE Can't be Null",Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.ETDescription.text.isEmpty()){
            Toast.makeText(this@AddPostActivity,"TITLE Can't be Null",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun apiCall() {
        val map = hashMapOf<String,String>()
        val selectedItem= binding.spCategory.selectedItem.toString()
        Log.e("TAG","${selectedItem}")

        map.put("title",binding.ETTitle.text.toString())
        map.put("content",binding.ETDescription.text.toString())
        val post = RetrofitHelper().retrofitobj()
        post.addPost(map,Constant.token,selectedItem,Constant.userdata.data.login.userDetails.id).enqueue(object :retrofit2.Callback<ModelAllpostresponse>{
            override fun onResponse(
                call: Call<ModelAllpostresponse>,
                response: Response<ModelAllpostresponse>
            ) {
                Log.e("POST","${response.body()}")
                Toast.makeText(this@AddPostActivity,response.body()!!.message,Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ModelAllpostresponse>, t: Throwable) {
                Toast.makeText(this@AddPostActivity,"Post Added Succesfully",Toast.LENGTH_SHORT).show()
            }

        })
        binding.ETTitle.text.clear()
        binding.ETDescription.text.clear()
    }

    fun setSpinner(){
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spCategory.adapter = adapter

        val category = RetrofitHelper().retrofitobj()
        category.getcategories(Constant.token).enqueue(object : retrofit2.Callback<ResponseCategory>{
            override fun onResponse(
                call: Call<ResponseCategory>,
                response: Response<ResponseCategory>
            ) {
                if (response.isSuccessful || response.body()!!.status=="ok"){
                    adapter.clear()
                    Log.e("TAG","${response.body()!!.data.categories}")
                    for (i in response.body()!!.data.categories){
                        adapter.add(i.catgTitle)
                    }
                    adapter.notifyDataSetChanged()
                }else{
                    Toast.makeText(this@AddPostActivity, response.body()!!.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseCategory>, t: Throwable) {
                Toast.makeText(this@AddPostActivity,"${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }
}