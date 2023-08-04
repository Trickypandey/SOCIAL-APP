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
import com.example.socialapp.databinding.FragmentAllPostBinding
import com.example.socialapp.utils.Constant
import com.example.socialapp.utils.RetrofitHelper
import retrofit2.Call
import retrofit2.Response


class AllPostFragment : Fragment() {
    lateinit var binding:FragmentAllPostBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_all_post, container, false)
        binding.postRv.layoutManager= LinearLayoutManager(requireContext())
        apiCall()
        return  binding.root
    }
    private fun apiCall() {
        val allpost= RetrofitHelper().retrofitobj()
        allpost.getAllPost(Constant.token).enqueue(object :retrofit2.Callback<ModelAllpostresponse>{
            override fun onResponse(call: Call<ModelAllpostresponse>, response: Response<ModelAllpostresponse>) {
//                Log.e("")
                if (response.isSuccessful || response.body()!!.status=="ok"){
                    binding.postRv.adapter= RvAdapterPost(response.body()!!.data.posts,requireActivity())
                    Log.e("TAG","${response.body()!!.data}")
                }else{
                    Toast.makeText(requireContext(), response.body()!!.message, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ModelAllpostresponse>, t: Throwable) {
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