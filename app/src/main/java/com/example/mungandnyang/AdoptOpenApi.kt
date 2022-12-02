package com.example.mungandnyang

import com.example.mungandnyang.animalData.AdoptAnimal
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

class AdoptOpenApi {
    companion object{
        const val DOMAIN = "http://openapi.seoul.go.kr:8088"
        const val API_KEY = BuildConfig.ADOPT_KEY
        const val LIMIT = 32
    }
}

interface AdoptOpenService{
    @GET("{api_key}/json/TbAdpWaitAnimalView/1/{end}")
    fun getAdopt(@Path("api_key") key : String, @Path("end") limit : Int): Call<AdoptAnimal>
}
