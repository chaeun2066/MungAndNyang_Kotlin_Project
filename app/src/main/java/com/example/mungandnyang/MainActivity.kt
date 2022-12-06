package com.example.mungandnyang

import android.app.ProgressDialog.show
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.viewpager.widget.PagerAdapter
import com.example.mungandnyang.databinding.ActivityMainBinding
import com.example.mungandnyang.databinding.TabLayoutItemBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var binding : ActivityMainBinding
    lateinit var adoptlistFragment: AdoptlistFragment
    lateinit var reviewFragment: ReviewFragment
    lateinit var userAuth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAuth = Firebase.auth

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

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_logout_24)
        supportActionBar?.setDisplayShowTitleEnabled(true)
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
        val eventHandler = object : DialogInterface.OnClickListener{
            override fun onClick(userDialog: DialogInterface?, p1: Int) {
                if(p1 == DialogInterface.BUTTON_POSITIVE){
                    userAuth.signOut()
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(applicationContext, "로그아웃 하셨습니다", Toast.LENGTH_SHORT).show()

                }
            }
        }
        when(item.itemId) {
            android.R.id.home -> {
                AlertDialog.Builder(this).run {
                    setTitle("알림")
                    setIcon(android.R.drawable.ic_dialog_alert)
                    setMessage("정말 로그아웃 하시겠습니까?")
                    setPositiveButton("네", eventHandler)
                    setNegativeButton("아니오", eventHandler)
                    show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}