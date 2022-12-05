package com.example.mungandnyang

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mungandnyang.databinding.ReviewItemBinding

class UploadAdapter(val context: Context, val uploadList: MutableList<UploadVO>): RecyclerView.Adapter<UploadAdapter.UploadViewHolder>() {

    override fun onBindViewHolder(wifiViewholder: UploadViewHolder, position: Int) {
        val binding = (wifiViewholder as UploadViewHolder).binding
        val uploadData = uploadList.get(position)

        binding.tvRevTitle.text = uploadData.name
        binding.tvRevDate.text = uploadData.date
        uploadData.docId
        val adoptDAO = AdoptDAO()
        val imgRef = adoptDAO.storage!!.reference.child("images/${uploadData.docId}.jpg")
        imgRef.downloadUrl.addOnCompleteListener {
            if(it.isSuccessful){
                Glide.with(context).load(it.result).into(binding.ivRevPicture)
                binding.ivRevHashtag.setImageResource(R.drawable.phone)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadViewHolder {
        var binding = ReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UploadViewHolder(binding)
    }

    override fun getItemCount() = uploadList.size

    class UploadViewHolder(val binding: ReviewItemBinding) : RecyclerView.ViewHolder(binding.root)
}