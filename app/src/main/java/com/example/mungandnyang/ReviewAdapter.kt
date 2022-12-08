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
    override fun onBindViewHolder(wifiViewholder: ReviewViewHolder, position: Int) {
        val binding = (wifiViewholder as ReviewViewHolder).binding
        val reviewData = reviewList.get(position)

        binding.tvRevTitle.isSelected = true
        binding.tvRevTitle.text = reviewData.title
        binding.tvRevDate.text = reviewData.date
        binding.ivRevPicture.setImageResource(reviewData.image)
        binding.revLinearLayout.setOnClickListener {
            binding.revLinearLayout.resources.getColor(R.color.iphone_blue)
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