package com.example.mungandnyang

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.mungandnyang.animalData.AdoptAnimal
import com.example.mungandnyang.animalPhoto.AnimalPhoto
import com.example.mungandnyang.databinding.FragmentReviewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReviewFragment : Fragment() {
    lateinit var binding: FragmentReviewBinding
    lateinit var adapter: ReviewAdapter
    lateinit var animalList: MutableList<AnimalVO>
    lateinit var photoList: MutableList<PhotoVO>
    var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReviewBinding.inflate(layoutInflater)
        animalList = mutableListOf()
        photoList = mutableListOf()

        val retrofit = Retrofit.Builder()
            .baseUrl(AdoptOpenApi.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val infoService = retrofit.create(AdoptOpenService::class.java)
        infoService.getAdopt(AdoptOpenApi.API_KEY, AdoptOpenApi.LIMIT).enqueue(object :
            Callback<AdoptAnimal> {
            override fun onResponse(call: Call<AdoptAnimal>, response: Response<AdoptAnimal>) {
                val data = response.body()
                data?.let{
                    it.TbAdpWaitAnimalView.list_total_count
                    for (loadData in it.TbAdpWaitAnimalView.row) {
                        val adoptDAO = AdoptDAO()
                        var number  = loadData.ANIMAL_NO
                        var state = loadData.ADP_STTUS
                        var name = loadData.NM
                        var age = loadData.AGE
                        var gender = loadData.SEXDSTN
                        var weight = loadData.BDWGH
                        var breed  = loadData.BREEDS
                        var date = loadData.ENTRNC_DATE
                        var intro  = loadData.INTRCN_CN
                        val info = AnimalVO(number, state, name, age, gender, weight, breed, date, intro)

                        animalList.add(AnimalVO(number, state, name, age, gender, weight, breed, date, intro))
                        adoptDAO.insertAnimal(info).addOnSuccessListener {
                        }?.addOnFailureListener {
                            Log.d("com.example.database", "infoData insert failed")
                        }
                        adapter = ReviewAdapter(binding.root.context, animalList, photoList)
                        binding.recyclerView.layoutManager = GridLayoutManager(binding.root.context, 2)
                        binding.recyclerView.setHasFixedSize(true)
                        binding.recyclerView.adapter = adapter
                    }
                }?: let{
                    Log.d("mungandnyang", "infoList is Empty")
                }
            }
            override fun onFailure(call: Call<AdoptAnimal>, t: Throwable) {
                Log.d("mungandnyang", "infoList Load Error ${t.printStackTrace()}")
            }
        })

        val photoService = retrofit.create(PhotoOpenService::class.java)
        photoService.getAnimal(PhotoOpenApi.API_KEY, PhotoOpenApi.LIMIT).enqueue(object :
            Callback<AnimalPhoto> {
            override fun onResponse(call: Call<AnimalPhoto>, response: Response<AnimalPhoto>) {
                val data = response.body()
                data?.let{
                    it.TbAdpWaitAnimalPhotoView.list_total_count
                    for (loadData in it.TbAdpWaitAnimalPhotoView.row) {
                        val adoptDAO = AdoptDAO()
                        var aniNumber = loadData.ANIMAL_NO
                        var photoKind = loadData.PHOTO_KND
                        var photoUrl = loadData.PHOTO_URL
                        if(photoKind.equals("THUMB")){
                            val photo = PhotoVO(aniNumber, photoKind, photoUrl)
                            photoList.add(PhotoVO(aniNumber, photoKind, photoUrl))
                            adoptDAO.insertPhoto(photo).addOnSuccessListener {
                            }?.addOnFailureListener {
                                Log.d("com.example.database", "photoData insert failed")
                            }
                        }
                        adapter = ReviewAdapter(binding.root.context, animalList, photoList)
                        binding.recyclerView.layoutManager = GridLayoutManager(binding.root.context, 2)
                        binding.recyclerView.setHasFixedSize(true)
                        binding.recyclerView.adapter = adapter
                    }
                }?: let{
                    Log.d("com.example.database", "photoList is Empty")
                }
            }
            override fun onFailure(call: Call<AnimalPhoto>, t: Throwable) {
                Log.d("com.example.test", "photoList Load Error ${t.printStackTrace()}")
            }
        })

        val requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == Activity.RESULT_OK){
                Glide.with(this)
                    .load(it.data?.data)
                    .centerCrop()
                imageUri = it.data?.data
            }
        }

        binding.fab.setOnClickListener {
            if(binding.fab.visibility == View.VISIBLE){
                binding.fabChat.visibility = View.VISIBLE
                binding.fabCam.visibility = View.VISIBLE
                binding.fab.setImageResource(R.drawable.minus)
                binding.fabChat.setOnClickListener {
                }
                binding.fabCam.setOnClickListener {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                    requestLauncher.launch(intent)
                }
                binding.fab.setOnClickListener {
                    binding.fabChat.visibility = View.INVISIBLE
                    binding.fabCam.visibility = View.INVISIBLE
                    binding.fab.setImageResource(R.drawable.add)
                }
            }
        }
        return binding.root
    }
}