package com.example.socialapp.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socialapp.Adapter.RvAdapterPost
import com.example.socialapp.DataModel.ModelAllpostresponse
import com.example.socialapp.R
import com.example.socialapp.databinding.FragmentSearchBinding
import com.example.socialapp.utils.Constant
import com.example.socialapp.utils.RetrofitHelper
import retrofit2.Call
import retrofit2.Response

class SearchFragment : Fragment() {

    lateinit var binding:FragmentSearchBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_search, container, false)
        allCLick()
        return binding.root
    }

    private fun allCLick() {
        binding.btnsearch.setOnClickListener{
            apiCall(binding.ETSearch.text.toString())
        }
    }

    private fun apiCall(key: String) {
        val search = RetrofitHelper().retrofitobj()

        search.searchPost(key,Constant.token).enqueue(object : retrofit2.Callback<ModelAllpostresponse>{
            override fun onResponse(
                call: Call<ModelAllpostresponse>,
                response: Response<ModelAllpostresponse>
            ){
                if (response.isSuccessful || response.body()!!.status=="ok"){

                    binding.postRv.layoutManager= LinearLayoutManager(requireContext())
                    binding.postRv.adapter = RvAdapterPost(response.body()!!.data.posts,requireActivity())
                }else{
                    Toast.makeText(requireContext(), response.body()!!.message, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ModelAllpostresponse>, t: Throwable) {
                Toast.makeText(requireContext(),"${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}