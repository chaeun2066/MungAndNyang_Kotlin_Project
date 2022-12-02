package com.example.mungandnyang

import android.content.Context
import android.content.Intent
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


class ReviewAdapter(val context: Context, val animalList: MutableList<AnimalVO>, val photoList: MutableList<PhotoVO>): RecyclerView.Adapter<ReviewAdapter.WifiViewHolder>() {
    lateinit var databaseReference: DatabaseReference
    var address: String? = null

    override fun onBindViewHolder(wifiViewholder: WifiViewHolder, position: Int) {
        val binding = (wifiViewholder as WifiViewHolder).binding
        databaseReference = Firebase.database.reference

        val animalData = animalList.get(position)

        databaseReference.child("ReviewAdapter").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(dataSnapshot in snapshot.children){
                    val animalPhoto = dataSnapshot.getValue(PhotoVO::class.java)
                    if(animalPhoto!!.ani_number.equals(animalData.number)){
                        Glide.with(context).load("https://${animalPhoto.photo_url}").into(binding.ivRevPicture)
                        address = "http://${animalPhoto.photo_url}"
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("com.example.database", "onCancelled")
            }
        })

        binding.tvRevName.text = animalData.name

        binding.revLinearLayout.setOnClickListener {
            val intent = Intent(binding.root.context, DetailInfoActivity::class.java)
            intent.putExtra("imageUrl", address)
            Log.d("com.example.database", "${address}")
            intent.putExtra("no", animalData.number)
            intent.putExtra("name", animalData.name)
            intent.putExtra("gender", animalData.gender)
            intent.putExtra("age", animalData.age)
            intent.putExtra("kind", animalData.breed)
            intent.putExtra("date", animalData.date)
            startActivity(binding.root.context, intent, null)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WifiViewHolder {
        var binding = ReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WifiViewHolder(binding)
    }

    override fun getItemCount() = animalList.size

    class WifiViewHolder(val binding: ReviewItemBinding) : RecyclerView.ViewHolder(binding.root)
}