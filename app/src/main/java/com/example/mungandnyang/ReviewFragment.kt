package com.example.mungandnyang

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mungandnyang.databinding.FragmentReviewBinding

class ReviewFragment : Fragment() {
    lateinit var binding: FragmentReviewBinding
    lateinit var reviewList: MutableList<ReviewVO>
    lateinit var adapter: ReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReviewBinding.inflate(layoutInflater)
        reviewList = mutableListOf()

        reviewList.add(
            ReviewVO(R.drawable.a20220908,
            "보리(추석)근황",
            "2022.09.08",
            "https://cafe.naver.com/seoulanimalcare/4356")
        )
        reviewList.add(
            ReviewVO(R.drawable.a20220910,
            "루다(루카) 잘 지내는 중입니다~",
            "2022.09.10",
            "https://cafe.naver.com/seoulanimalcare/4356")
        )
        reviewList.add(
            ReviewVO(R.drawable.a20220912,
            "루루(명순) 9월 소식이예요.",
            "2022.09.12",
            "https://cafe.naver.com/seoulanimalcare/4356")
        )
        reviewList.add(
            ReviewVO(R.drawable.a20220914,
            "얀돌이에용 너무 오랜만이에요",
            "2022.09.14",
            "https://cafe.naver.com/seoulanimalcare/4356")
        )
        reviewList.add(
            ReviewVO(R.drawable.a20221013,
            "마리 소식 전해요!",
            "2022.10.13",
            "https://cafe.naver.com/seoulanimalcare/4356")
        )
        reviewList.add(
            ReviewVO(R.drawable.a20221024,
            "맥스(삼식) 10월 소식이에요~",
            "2022.10.24",
            "https://cafe.naver.com/seoulanimalcare/4356")
        )
        reviewList.add(
            ReviewVO(R.drawable.a20221028,
            "밀 &콩 일상 (병아리&삐약이)",
            "2022.10.28",
            "https://cafe.naver.com/seoulanimalcare/4356")
        )
        reviewList.add(
            ReviewVO(R.drawable.a20221118,
            "리치(산돌) 유리(율이) 소식입니다~",
            "2022.11.18",
            "https://cafe.naver.com/seoulanimalcare/4356")
        )
        reviewList.add(
            ReviewVO(R.drawable.a20221124,
            "루루(명순) 11월 소식입니다.",
            "2022.11.24",
            "https://cafe.naver.com/seoulanimalcare/4356")
        )
        reviewList.add(
            ReviewVO(R.drawable.a20221128,
            "안녕하세요~ 버들이 소식 오랜만에 남기러 왔어요~",
            "2022.11.28",
            "https://cafe.naver.com/seoulanimalcare/4356")
        )

        adapter = ReviewAdapter(binding.root.context, reviewList)
        binding.recyclerView.layoutManager = GridLayoutManager(binding.root.context, 2)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter

        binding.fab.setOnClickListener {
            var flag = false
            if(flag == false){
                binding.fabChat.visibility = View.VISIBLE
                binding.fabCam.visibility = View.VISIBLE
                binding.fab.setImageResource(R.drawable.minus)
                binding.fabChat.setOnClickListener {
                    val intent = Intent(binding.root.context, ChatActivity::class.java)
                    startActivity(intent)
                }
                binding.fabCam.setOnClickListener {
                    val intent = Intent(binding.root.context, ReviewUploadActivity::class.java)
                    startActivity(intent)
                }
                flag = true
            }else {
                binding.fab.setOnClickListener {
                    binding.fabChat.visibility = View.INVISIBLE
                    binding.fabCam.visibility = View.INVISIBLE
                    binding.fab.setImageResource(R.drawable.add)
                }
                flag = false
            }
            return@setOnClickListener
        }
        return binding.root
    }
}