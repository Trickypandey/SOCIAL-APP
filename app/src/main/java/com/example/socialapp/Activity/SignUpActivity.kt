package com.example.socialapp.Activity

import ResponseSigupModel
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.socialapp.R
import com.example.socialapp.databinding.ActivitySignUpBinding
import com.example.socialapp.utils.RetrofitHelper
import retrofit2.Call
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    lateinit var binding :ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up)

        allCLick()
    }

    private fun allCLick() {
        binding.btnLogin.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignin.setOnClickListener {
            if (validateInputs()) {
              apiCall()
            }
        }
    }

    private fun apiCall() {
        val map = hashMapOf<String,String>()
        map["name"]=binding.etUserName.text.toString()
        map["email"] = binding.etEmailAddressLogin.text.toString()
        map["password"] = binding.etPassword.text.toString()
        map["about"] = "fdkbafvkjasvdkfjvaskjdfvjkavsdfkjvajskvfkj"
        val signup = RetrofitHelper().retrofitobj()

        signup.addUser(map).enqueue(object : retrofit2.Callback<ResponseSigupModel>{
            override fun onResponse(
                call: Call<ResponseSigupModel>,
                response: Response<ResponseSigupModel>
            ) {
                if (response.body()!!.status == "OK"){
                    Toast.makeText(this@SignUpActivity, response.body()!!.message,Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@SignUpActivity,LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this@SignUpActivity, response.body()!!.message,Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseSigupModel>, t: Throwable) {
                Toast.makeText(this@SignUpActivity, t.message,Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun validateInputs(): Boolean {
        val email = binding.etEmailAddressLogin.text.toString().trim()
        val username = binding.etUserName.text.toString().trim()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etconfirmPassword.text.toString()

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmailAddressLogin.error = "Invalid email address"
            binding.etEmailAddressLogin.requestFocus()
            return false
        } else {
            binding.etEmailAddressLogin.error = null
            binding.etEmailAddressLogin.clearFocus()
        }

        if (username.isEmpty()) {
            binding.etUserName.error = "Username is required"
            binding.etUserName.requestFocus()
            return false
        } else {
            binding.etUserName.error = null
            binding.etUserName.clearFocus()
        }

        if (password.isEmpty()) {
            binding.etPassword.error = "Password is required"
            binding.etPassword.requestFocus()
            return false
        } else {
            binding.etPassword.error = null
            binding.etPassword.clearFocus()
        }

        if (confirmPassword.isEmpty()) {
            binding.etconfirmPassword.error = "Confirm password is required"
            binding.etconfirmPassword.requestFocus()
            return false
        } else {
            binding.etconfirmPassword.error = null
            binding.etconfirmPassword.clearFocus()
        }

        if (password != confirmPassword) {
            binding.etconfirmPassword.error = "Passwords do not match"
            binding.etconfirmPassword.requestFocus()
            return false
        } else {
            binding.etconfirmPassword.error = null
            binding.etconfirmPassword.clearFocus()
        }

        return true
    }

}