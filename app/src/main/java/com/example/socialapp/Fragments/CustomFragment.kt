package com.example.socialapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.socialapp.R

class CustomFragment : Fragment() {
    companion object {
        fun newInstance(title: String): CustomFragment {
            val fragment = CustomFragment()
            val args = Bundle()
            args.putString("title", title)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_custom, container, false)

        // Access the title from arguments
        val title = arguments?.getString("title")

        // Perform any necessary operations with the title or customize the fragment's view

        return view
    }
}