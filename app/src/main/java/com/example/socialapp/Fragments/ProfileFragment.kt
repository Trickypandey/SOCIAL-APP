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
import com.example.socialapp.DataModel.Category
import com.example.socialapp.DataModel.ModelAllpostresponse
import com.example.socialapp.DataModel.Post
import com.example.socialapp.DataModel.User
import com.example.socialapp.R
import com.example.socialapp.databinding.FragmentProfileBinding
import com.example.socialapp.utils.Constant
import com.example.socialapp.utils.RetrofitHelper
import retrofit2.Call
import retrofit2.Response

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.tvUsername.text = Constant.userdata.data.login.userDetails.name
        binding.userPost.layoutManager = LinearLayoutManager(requireContext())
        api()
        return binding.root
    }

    private fun api() {
        val allpost = RetrofitHelper().retrofitobj()
        allpost.getPostByUser(Constant.token, Constant.userdata.data.login.userDetails.id)
            .enqueue(object : retrofit2.Callback<ModelAllpostresponse> {
                override fun onResponse(
                    call: Call<ModelAllpostresponse>,
                    response: Response<ModelAllpostresponse>
                ) {
                    if (response.isSuccessful && response.body()?.status == "ok") {
                        val posts = response.body()?.data!!.posts
                        if (posts != null) {
                            binding.userPost.adapter = RvAdapterPost(posts, requireActivity())
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "No posts found for the user.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            response.body()?.message ?: "Error fetching posts.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ModelAllpostresponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            api()
        }
    }

    override fun onResume() {
        super.onResume()
        api()
    }
}