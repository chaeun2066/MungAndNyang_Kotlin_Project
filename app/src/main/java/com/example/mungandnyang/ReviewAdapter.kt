package com.example.mungandnyang

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.mungandnyang.databinding.ReviewItemBinding


class ReviewAdapter(val context: Context, val reviewList: MutableList<ReviewVO>): RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {
    override fun onBindViewHolder(reviewViewholder: ReviewViewHolder, position: Int) {
        val binding = (reviewViewholder as ReviewViewHolder).binding
        val reviewData = reviewList.get(position)

        binding.tvRevTitle.isSelected = true
        binding.tvRevTitle.text = reviewData.title
        binding.tvRevDate.text = reviewData.date
        binding.ivRevPicture.setImageResource(reviewData.image)
        // 리뷰 itemview 선택 시 intent 이동
        binding.revLinearLayout.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.data = Uri.parse(reviewData.url)
            startActivity(binding.root.context, intent, null)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        var binding = ReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    override fun getItemCount() = reviewList.size

    class ReviewViewHolder(val binding: ReviewItemBinding) : RecyclerView.ViewHolder(binding.root)
}