package com.example.mungandnyang

import com.example.mungandnyang.animalPhoto.AnimalPhoto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

class PhotoOpenApi {
    companion object{
        const val DOMAIN = "http://openapi.seoul.go.kr:8088"
        const val API_KEY = BuildConfig.PHOTO_KEY
        const val LIMIT = 300
    }
}

interface PhotoOpenService{
    @GET("{api_key}/json/TbAdpWaitAnimalPhotoView/1/{end}")
    fun getAnimal(@Path("api_key") key : String, @Path("end") limit : Int): Call<AnimalPhoto>
}