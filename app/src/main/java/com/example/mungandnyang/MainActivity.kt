package com.example.mungandnyang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.viewpager.widget.PagerAdapter
import com.example.mungandnyang.databinding.ActivityMainBinding
import com.example.mungandnyang.databinding.TabLayoutItemBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var binding : ActivityMainBinding
    lateinit var adoptlistFragment: AdoptlistFragment
    lateinit var reviewFragment: ReviewFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Toolbar 설정
        setSupportActionBar(binding.toolbar)

        //viewPagerAdapter 설정
        val pagerAdapter = PagerAdapter(this)
        val title = mutableListOf<String>("보호 동물", "입양 후기")

        adoptlistFragment = AdoptlistFragment()
        reviewFragment = ReviewFragment()

        pagerAdapter.addFragment(adoptlistFragment, title[0])
        pagerAdapter.addFragment(reviewFragment, title[1])

        binding.mainViewPager2.adapter = pagerAdapter

        TabLayoutMediator(binding.mainTabLayout, binding.mainViewPager2){ tab, position ->
            tab.setCustomView(createTabView(title[position]))
        }.attach()
    }

    private fun createTabView(title: String): View {
        val useTabBinding = TabLayoutItemBinding.inflate(layoutInflater)
        useTabBinding.tvTitle.text = title
        return useTabBinding.root
    }

    //Toolbar 메뉴 기능 설정
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)

        val menuItem = menu?.findItem(R.id.menu_chat)

        menuItem?.setOnMenuItemClickListener {
            val intent = Intent(this, FriendslistActivity::class.java)
            startActivity(intent)
            true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}