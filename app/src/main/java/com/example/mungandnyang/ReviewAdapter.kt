package com.example.mungandnyang

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mungandnyang.databinding.ReviewItemBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ReviewAdapter(val context: Context, val reviewList: MutableList<ReviewVO>): RecyclerView.Adapter<ReviewAdapter.WifiViewHolder>() {
    var address: String? = null

    override fun onBindViewHolder(wifiViewholder: WifiViewHolder, position: Int) {
        val binding = (wifiViewholder as WifiViewHolder).binding

        val reviewData = reviewList.get(position)

        binding.tvRevTitle.text = reviewData.title
        binding.tvRevDate.text = reviewData.date
        Glide.with(context)
            .load("https://cafe.naver.com/common/storyphoto/viewer.html?src=https%3A%2F%2Fcafeptthumb-phinf.pstatic.net%2FMjAyMjA5MDhfMjc0%2FMDAxNjYyNjE0NzIxNTY2.0tjbLn317ChbfqJ-oY2AazlnOSVLU7Kds8TE3M48rF8g.qLnn1UHJ8oyG0lpc-zmI3j3RVHlR3oA4nPTXBUAuFmog.JPEG%2F20220820%25EF%25BC%25BF101630.jpg%3Ftype%3Dw1600")
            .centerCrop()
            .into(binding.ivRevPicture)
        Log.d("mungandnyang", "${reviewData.image}")

        binding.revLinearLayout.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.data = Uri.parse(reviewData.url)
            startActivity(binding.root.context, intent, null)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WifiViewHolder {
        var binding = ReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WifiViewHolder(binding)
    }

    override fun getItemCount() = reviewList.size

    class WifiViewHolder(val binding: ReviewItemBinding) : RecyclerView.ViewHolder(binding.root)
}