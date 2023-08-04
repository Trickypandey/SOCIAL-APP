package com.example.socialapp.Activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.socialapp.Fragments.Category1Fragment
import com.example.socialapp.Fragments.HomeFragment
import com.example.socialapp.Fragments.SearchFragment
import com.example.socialapp.Fragments.ProfileFragment
import com.example.socialapp.R
import com.example.socialapp.databinding.ActivityMainBinding
import com.example.socialapp.utils.Common
import com.example.socialapp.utils.Constant

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        Constant.flag = if (Common().isUserAdmin(Constant.userdata.data.login.userDetails)) {
            1
        } else {
            0
        }

        allClick()

        addOrShowFragment(HomeFragment())
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menuitem_1 -> {
                    binding.title.text = "Home"
                    binding.logout.visibility = View.GONE
                    binding.addcategories.visibility = View.GONE
                    addOrShowFragment(HomeFragment())
                }
                R.id.menuitem_2 -> {
                    binding.title.text = "Search"
                    binding.logout.visibility = View.GONE
                    binding.addcategories.visibility = View.GONE
                    addOrShowFragment(SearchFragment())
                }
                R.id.menuitem_3 -> {
                    binding.title.text = "Profile"
                    binding.logout.visibility = View.VISIBLE
                    binding.addcategories.visibility = View.GONE
                    addOrShowFragment(ProfileFragment())
                }
                R.id.menuitem_4 -> {
                    binding.title.text = "Category"
                    binding.logout.visibility = View.GONE

                    if (Common().isUserAdmin(Constant.userdata.data.login.userDetails)){
                         binding.addcategories.visibility = View.VISIBLE
                    }
                    addOrShowFragment(Category1Fragment())
                }
            }

            // Set the checked state of the selected item to true
            item.isChecked = true

            // Return true to indicate that the item selection event has been handled
            return@setOnItemSelectedListener true
        }

    }

    private fun allClick() {
        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this,AddPostActivity::class.java)
            startActivity(intent)
        }


        binding.logout.setOnClickListener {
            val sh = getSharedPreferences("userDetail", MODE_PRIVATE).edit()
            sh.clear()
            sh.commit()
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.addcategories.setOnClickListener {
            val intent = Intent(this,AddCategoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addOrShowFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTag = fragment.javaClass.simpleName
        val currentFragment1 = fragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)
        val currentFragment2 = fragmentManager.findFragmentByTag(SearchFragment::class.java.simpleName)
        val currentFragment3 = fragmentManager.findFragmentByTag(ProfileFragment::class.java.simpleName)
        val currentFragment4 = fragmentManager.findFragmentByTag(Category1Fragment::class.java.simpleName)

        val transaction = fragmentManager.beginTransaction()

        if (currentFragment1 != null && currentFragment1.isVisible) {

            if (fragmentTag == currentFragment1.tag) {

                // Fragment already added and visible, do nothing
                return

            }

            transaction.hide(currentFragment1)

        }

        if (currentFragment2 != null && currentFragment2.isVisible) {
            if (fragmentTag == currentFragment2.tag) {

                // Fragment already added and visible, do nothing
                return

            }

            transaction.hide(currentFragment2)

        }

        if (currentFragment3 != null && currentFragment3.isVisible) {
            if (fragmentTag == currentFragment3.tag) {

                // Fragment already added and visible, do nothing
                return

            }

            transaction.hide(currentFragment3)

        }
        if (currentFragment4 != null && currentFragment4.isVisible) {
            if (fragmentTag == currentFragment4.tag) {

                // Fragment already added and visible, do nothing
                return

            }

            transaction.hide(currentFragment4)

        }


        val existingFragment = fragmentManager.findFragmentByTag(fragmentTag)

        if (existingFragment != null) {

            // Fragment is already added, so show it
            transaction.show(existingFragment)

        } else {
            // Fragment is not added, so add it
            transaction.add(R.id.frame_layout, fragment, fragmentTag)
        }

        transaction.commit()
    }

    override fun onResume() {
        super.onResume()
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
    }

}