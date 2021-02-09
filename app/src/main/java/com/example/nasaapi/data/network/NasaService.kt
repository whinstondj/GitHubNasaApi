package com.example.nasaapi.data.network

import com.example.nasaapi.data.model.NasaResponseDataModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface NasaService {
        @GET("search")
        fun getNasaImages(@Query("q") pictureType: String): Call<NasaResponseDataModel>
}