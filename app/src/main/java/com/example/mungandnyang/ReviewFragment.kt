package com.example.mungandnyang

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
    var imageUri: Uri? = null

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
            ReviewVO("https://cafe.naver.com/common/storyphoto/viewer.html?src=https%3A%2F%2Fcafeptthumb-phinf.pstatic.net%2FMjAyMjA5MDhfMjc0%2FMDAxNjYyNjE0NzIxNTY2.0tjbLn317ChbfqJ-oY2AazlnOSVLU7Kds8TE3M48rF8g.qLnn1UHJ8oyG0lpc-zmI3j3RVHlR3oA4nPTXBUAuFmog.JPEG%2F20220820%25EF%25BC%25BF101630.jpg%3Ftype%3Dw1600",
            "보리(추석)근황",
            "2022.09.08",
            "https://cafe.naver.com/seoulanimalcare/4356")
        )
        reviewList.add(
            ReviewVO("https://cafe.naver.com/common/storyphoto/viewer.html?src=https%3A%2F%2Fcafeptthumb-phinf.pstatic.net%2FMjAyMjA5MTBfMjAy%2FMDAxNjYyNzk4MTgwMDc3.it9ncT5EoNazdRTw4GPdju0-x4ATebpi4WqMZH2H6yYg.B6soBYBh-oQfUy5LKAFKU-GLJby-NTQysZ46yshxIJgg.JPEG%2Fbeauty_20220902083955.jpg%3Ftype%3Dw1600",
            "루다(루카) 잘 지내는 중입니다~",
            "2022.09.10",
            "https://cafe.naver.com/seoulanimalcare/4356")
        )
        reviewList.add(
            ReviewVO("https://cafe.naver.com/common/storyphoto/viewer.html?src=https%3A%2F%2Fcafeptthumb-phinf.pstatic.net%2FMjAyMjA5MTJfMTU4%2FMDAxNjYyOTYzNjI1MzI5.RyEskkfAmDTIVbZ_5mxVbst__eza2ZFFIn-OPv_I_BUg.1D1srLcGmQrUnnR--4wMN59ocjgMWceUSdxs03jY33Eg.JPEG%2F5C5D5D72-278E-44C4-95D8-4E545CAAB138.jpeg%3Ftype%3Dw1600",
            "루루(명순) 9월 소식이예요.",
            "2022.09.12",
            "https://cafe.naver.com/seoulanimalcare/4356")
        )
        reviewList.add(
            ReviewVO("https://cafe.naver.com/common/storyphoto/viewer.html?src=https%3A%2F%2Fcafeptthumb-phinf.pstatic.net%2FMjAyMjA5MTRfMTMz%2FMDAxNjYzMTMxMzYxMjQ3.bPNtrnREJx2ASF4rSr_vbDNrI27B3nKgafEB1MxBPIMg.tiiaP8Q1AyvB_--Usfj7cTK2v_QdsnKkZkG_fA247m8g.JPEG%2FD7523C08-79E6-43A6-9A7E-7A41E0BF0B23.jpeg%3Ftype%3Dw1600",
            "얀돌이에용 너무 오랜만이에요",
            "2022.09.14",
            "https://cafe.naver.com/seoulanimalcare/4356")
        )
        reviewList.add(
            ReviewVO("https://cafe.naver.com/common/storyphoto/viewer.html?src=https%3A%2F%2Fcafeptthumb-phinf.pstatic.net%2FMjAyMjEwMTNfMTY1%2FMDAxNjY1NTg5ODA5MDU5.zxdsFxi3R06nBO1b2PQGfUJIHaiJMrG1OT4cfs_lb4Eg.qYUP0MPyPHq77_yRpf3bDOeMKApKx9mRR9TjTkSK4Vog.JPEG%2FIMG_1051.jpg%3Ftype%3Dw1600",
            "마리 소식 전해요!",
            "2022.10.13",
            "https://cafe.naver.com/seoulanimalcare/4356")
        )
        reviewList.add(
            ReviewVO("https://cafe.naver.com/common/storyphoto/viewer.html?src=https%3A%2F%2Fcafeptthumb-phinf.pstatic.net%2FMjAyMjEwMjRfNiAg%2FMDAxNjY2NTM3NDAzODAw.VFy7anMLGhFM5qzE6BMmHXoFtLY5ddujuW9obM4sdksg.ZGv_sX3OEQjIjtryoLotVF8TPZf7BnB21n76UQuKP04g.JPEG%2F20221023_165617.jpg%3Ftype%3Dw1600",
            "맥스(삼식) 10월 소식이에요~",
            "2022.10.24",
            "https://cafe.naver.com/seoulanimalcare/4356")
        )
        reviewList.add(
            ReviewVO("https://cafe.naver.com/common/storyphoto/viewer.html?src=https%3A%2F%2Fcafeptthumb-phinf.pstatic.net%2FMjAyMjEwMjhfNDgg%2FMDAxNjY2OTQxOTcwOTMx.qT1BY_t_cprhVTiVQi47K8MpSBGbjduU_v8R6irRzPgg.WisIJt-qUE3gkLAbwnxuzVPE40SHXE7ypTTynRrgNgQg.JPEG%2F20221023_135848.jpg%3Ftype%3Dw1600",
            "밀 &콩 일상 (병아리&삐약이)",
            "2022.10.28",
            "https://cafe.naver.com/seoulanimalcare/4356")
        )
        reviewList.add(
            ReviewVO("https://cafe.naver.com/common/storyphoto/viewer.html?src=https%3A%2F%2Fcafeptthumb-phinf.pstatic.net%2FMjAyMjExMThfMTM0%2FMDAxNjY4NzM4NTY2NTA2.KMTHJpO1xHKaS2jPcBxiNuW5NX05Tj4EFzTjqBbFRw8g.q4HJrAHLpLNGbb0sUrOIwPcySAIgt8CY2lfkh_GX02og.JPEG%2F20221117_143146.jpg%3Ftype%3Dw1600",
            "리치(산돌) 유리(율이) 소식입니다~",
            "2022.11.18",
            "https://cafe.naver.com/seoulanimalcare/4356")
        )
        reviewList.add(
            ReviewVO("https://cafe.naver.com/common/storyphoto/viewer.html?src=https%3A%2F%2Fcafeptthumb-phinf.pstatic.net%2FMjAyMjExMjRfMTQw%2FMDAxNjY5Mjc1MzgxODg5.-HaP44UQ8yBsrQpOMSMy1IQ5sn1vtSHCnyxndzvz6wsg.PlFJvIpcA-LQy5xBQoFo_TAlrZ0jwPLQcA4EKJ7zKrkg.JPEG%2F8109609A-59B7-41AF-A7AE-14C0C3AE3495.jpeg%3Ftype%3Dw1600",
            "루루(명순) 11월 소식입니다.",
            "2022.11.24",
            "https://cafe.naver.com/seoulanimalcare/4356")
        )
        reviewList.add(
            ReviewVO("https://cafe.naver.com/common/storyphoto/viewer.html?src=https%3A%2F%2Fcafeptthumb-phinf.pstatic.net%2FMjAyMjExMjhfMTI0%2FMDAxNjY5NjM5ODQwODc0.OQB0o6mtJg-MUx3-tX7xZveaZ-qr-38fWyoM2HQ_3_gg.wKK_X8MxfS3JVMee8zJKHYWG5OkzUAfGr9Ncavr0frEg.JPEG%2FIMG_0028.jpeg%3Ftype%3Dw1600",
            "안녕하세요~ 버들이 소식 오랜만에 남기러 왔어요~",
            "2022.11.28",
            "https://cafe.naver.com/seoulanimalcare/4356")
        )

        adapter = ReviewAdapter(binding.root.context, reviewList)
        binding.recyclerView.layoutManager = GridLayoutManager(binding.root.context, 2)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter

        val requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == Activity.RESULT_OK){
                val intent = Intent(binding.root.context, ReviewUploadActivity::class.java)
                imageUri = it.data?.data
                intent.putExtra("uploadImage", imageUri)
                startActivity(intent)
            }
        }

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
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                    requestLauncher.launch(intent)
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