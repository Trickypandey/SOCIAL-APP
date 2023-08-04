package com.example.socialapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socialapp.Adapter.RvAdapterCategory
import com.example.socialapp.DataModel.ResponseCategory
import com.example.socialapp.R
import com.example.socialapp.databinding.FragmentCategory1Binding
import com.example.socialapp.utils.Constant
import com.example.socialapp.utils.RetrofitHelper
import retrofit2.Call
import retrofit2.Response

class Category1Fragment : Fragment() {
    lateinit var binding :FragmentCategory1Binding
override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_category1, container, false)
        apiCall()
        return binding.root
    }
    fun apiCall(){

        val category = RetrofitHelper().retrofitobj()
        category.getcategories(Constant.token).enqueue(object :retrofit2.Callback<ResponseCategory>{
            override fun onResponse(
                call: Call<ResponseCategory>,
                response: Response<ResponseCategory>
            ) {
                if (response.isSuccessful){
                    binding.rvCategory.layoutManager = LinearLayoutManager(requireContext())
                    binding.rvCategory.adapter = RvAdapterCategory(response.body()!!.data.categories,requireActivity())
                }
                else{
                    Toast.makeText(requireContext(), response.body()!!.message, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResponseCategory>, t: Throwable) {
                Toast.makeText(requireContext(),"${t.message}", Toast.LENGTH_SHORT).show()
            }

        })

    }
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden){
            apiCall()
        }
    }

    override fun onResume() {
        super.onResume()
        apiCall()
    }
}