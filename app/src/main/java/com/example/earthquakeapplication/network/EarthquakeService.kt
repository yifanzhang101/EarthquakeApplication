package com.example.earthquakeapplication.network

import com.example.earthquakeapplication.model.EarthquakeResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EarthquakeService {

    @GET("fdsnws/event/1/query")
    fun getEarthquakes(
        @Query("format") format: String,
        @Query("starttime") startTime: String,
        @Query("endtime") endTime: String,
        @Query("minmagnitude") minMagnitude: Double
    ): Call<EarthquakeResponseModel>
}
