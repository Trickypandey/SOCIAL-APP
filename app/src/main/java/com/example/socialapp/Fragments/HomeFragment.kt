package com.example.socialapp.Fragments

import PageAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socialapp.Adapter.RvAdapterPost
import com.example.socialapp.DataModel.Category
import com.example.socialapp.DataModel.ModelAllpostresponse
import com.example.socialapp.DataModel.Post
import com.example.socialapp.DataModel.User
import com.example.socialapp.R
import com.example.socialapp.databinding.FragmentHomeBinding
import com.example.socialapp.utils.Constant
import com.example.socialapp.utils.RetrofitHelper
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response
class HomeFragment : Fragment() {
lateinit var binding:FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        Log.e("TAG","${Constant.token}")
        apiCall()
//        tempData()
//        binding.viewPager.adapter = fragmentManager?.let { PageAdapter(it) }
//
//        binding.tabLayout.setupWithViewPager(binding.viewPager)
        return binding.root
    }
    private fun apiCall() {
        val allpost= RetrofitHelper().retrofitobj()
        allpost.getAllPost(Constant.token).enqueue(object :retrofit2.Callback<ModelAllpostresponse>{
            override fun onResponse(call: Call<ModelAllpostresponse>, response: Response<ModelAllpostresponse>) {
//                Log.e("")

                if (response.isSuccessful || response.body()!!.status=="ok"){
                    binding.postRv.layoutManager=LinearLayoutManager(requireContext())
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
//    private fun tempData(){
//        binding.postRv.layoutManager=LinearLayoutManager(requireContext())
//        val list = arrayListOf<Post>()
//        list.add(
//            Post(1,"Sourabh","dfkaskbfkjabsdjkfbkjsabdfjkbaskjfbjk","idniadifisndfnidniasid",
//                Category(1), User(12,"sourabh","sourabhpandey@gmail.com"),"1211212"
//            )
//        )
//        list.add(
//            Post(1,"Sourabh","dfkaskbfkjabsdjkfbkjsabdfjkbaskjfbjk","idniadifisndfnidniasid",
//                Category(1), User(12,"sourabh","sourabhpandey@gmail.com"),"1211212"
//            )
//        )
//        list.add(
//            Post(1,"Sourabh","dfkaskbfkjabsdjkfbkjsabdfjkbaskjfbjk","idniadifisndfnidniasid",
//                Category(1), User(12,"sourabh","sourabhpandey@gmail.com"),"1211212"
//            )
//        )
//        list.add(
//            Post(1,"Sourabh","dfkaskbfkjabsdjkfbkjsabdfjkbaskjfbjk","idniadifisndfnidniasid",
//                Category(1), User(12,"sourabh","sourabhpandey@gmail.com"),"1211212"
//            )
//        )
//        list.add(
//            Post(1,"Sourabh","dfkaskbfkjabsdjkfbkjsabdfjkbaskjfbjk","idniadifisndfnidniasid",
//                Category(1), User(12,"sourabh","sourabhpandey@gmail.com"),"1211212"
//            )
//        )
//        list.add(
//            Post(1,"Sourabh","dfkaskbfkjabsdjkfbkjsabdfjkbaskjfbjk","idniadifisndfnidniasid",
//                Category(1), User(12,"sourabh","sourabhpandey@gmail.com"),"1211212"
//            )
//        )
//        list.add(
//            Post(1,"Sourabh","dfkaskbfkjabsdjkfbkjsabdfjkbaskjfbjk","idniadifisndfnidniasid",
//                Category(1), User(12,"sourabh","sourabhpandey@gmail.com"),"1211212"
//            )
//        )
//        list.add(
//            Post(1,"Sourabh","dfkaskbfkjabsdjkfbkjsabdfjkbaskjfbjk","idniadifisndfnidniasid",
//                Category(1), User(12,"sourabh","sourabhpandey@gmail.com"),"1211212"
//            )
//        )
//
//
//        binding.postRv.adapter= RvAdapterPost(list,requireActivity(),0)
//    }

    override fun onResume() {
        super.onResume()
//        apiCall()
    }

}