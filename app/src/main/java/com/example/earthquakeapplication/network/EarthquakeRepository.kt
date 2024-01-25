package com.example.earthquakeapplication.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object EarthquakeRepository {

    private val earthquakeService = ServiceCreator.retrofit.create(EarthquakeService::class.java)

    suspend fun getEarthquakeData(
        format: String,
        startTime: String,
        endTime: String,
        minMagnitude: Double
    ) = earthquakeService.getEarthquakes(format, startTime, endTime, minMagnitude).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}
