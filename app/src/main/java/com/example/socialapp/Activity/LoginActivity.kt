package com.example.socialapp.Activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.socialapp.DataModel.LoginRequest
import com.example.socialapp.DataModel.ModelAllpostresponse
import com.example.socialapp.DataModel.ModelClassLoginResponse
import com.example.socialapp.Fragments.HomeFragment
import com.example.socialapp.R
import com.example.socialapp.databinding.ActivityLoginBinding
import com.example.socialapp.utils.Constant
import com.example.socialapp.utils.RetrofitHelper
import com.google.gson.Gson
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var sh : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        sh = getSharedPreferences("userDetail", MODE_PRIVATE)
        val data = retrieveUserData()
        if (data != null){
            Constant.token = "Bearer " + data.data.login.token
            Constant.userdata = data
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        allClick()
    }

    private fun allClick() {
        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmailAddressLogin.text.toString().trim()
            val password = binding.etPasswordLogin.text.toString().trim()
            apiCall(email, password)
//            if (isValidCredentials(email, password)) {
//                apiCall(email, password)
//            } else {
//                Toast.makeText(this, "Invalid email or password.", Toast.LENGTH_SHORT).show()
//            }
        }
    }
    fun isValidCredentials(email: String, password: String): Boolean {
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordValid = password.length >=0 // Example: Minimum 6 characters for password

        return isEmailValid && isPasswordValid
    }

    fun apiCall(email: String, password: String) {
        val login = RetrofitHelper().retrofitobj()
        login.login(LoginRequest(email,password)).enqueue(object : retrofit2.Callback<ModelClassLoginResponse> {
            override fun onResponse(
                call: Call<ModelClassLoginResponse>,
                response: Response<ModelClassLoginResponse>
            ) {
                if (response.body()!!.status == "OK") {
                    val gson = Gson()
                    val json = gson.toJson(response.body())
                    val edit = sh.edit()
                    edit.putString("data", json)
                    edit.apply()
                    Constant.userdata= response.body()!!
                    Constant.token= "Bearer "+response.body()!!.data.login.token

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else {
                    Toast.makeText(this@LoginActivity, response.body()!!.message,Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ModelClassLoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, t.message,Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun retrieveUserData(): ModelClassLoginResponse? {
        val json = sh.getString("data", null)
        return if (json != null) {
            val gson = Gson()
            gson.fromJson(json, ModelClassLoginResponse::class.java)
        } else {
            null
        }
    }
    override fun onResume() {
        super.onResume()
        window.statusBarColor = ContextCompat.getColor(this, R.color.bluecolor)
    }
}