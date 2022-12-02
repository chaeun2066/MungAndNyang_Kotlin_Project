package com.example.mungandnyang

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mungandnyang.databinding.AdoptItemBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AdoptAdapter(val context: Context, val adoptList: MutableList<AnimalVO>):RecyclerView.Adapter<AdoptAdapter.AdoptViewHolder>() {
    lateinit var animalDatabase : DatabaseReference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdoptViewHolder {
        val binding = AdoptItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return AdoptViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdoptViewHolder, position: Int) {
        val binding = (holder as AdoptViewHolder).binding
        val adoptAnimal = adoptList[position]
        animalDatabase = Firebase.database.reference

        binding.tvName.text = adoptAnimal.name
        binding.tvBreed.text = adoptAnimal.breed
        binding.tvAge.text = adoptAnimal.age
        binding.tvWeight.text = adoptAnimal.weight + "kg"
        binding.tvDate.text = adoptAnimal.date

        if(adoptAnimal.gender.equals("W")){
            binding.ivGender.setImageResource(R.drawable.female)
        }else{
            binding.ivGender.setImageResource(R.drawable.male)
        }

        animalDatabase.child("AdoptPhoto").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(postSnapshot in snapshot.children){
                    val currentAnimal = postSnapshot.getValue(PhotoVO::class.java)
                    if(currentAnimal!!.ani_number == adoptAnimal.number){
                        Glide.with(context).load("https://${currentAnimal!!.photo_url}").override(300,250).into(binding.ivThumb)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    override fun getItemCount(): Int {
        return adoptList.size
    }

    class AdoptViewHolder(val binding: AdoptItemBinding): RecyclerView.ViewHolder(binding.root)
}