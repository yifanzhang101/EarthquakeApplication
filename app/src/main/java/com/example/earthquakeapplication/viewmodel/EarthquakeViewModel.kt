package com.example.earthquakeapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.earthquakeapplication.network.EarthquakeRepository
import com.example.earthquakeapplication.model.EarthquakeFeature
import kotlinx.coroutines.launch
import retrofit2.HttpException

class EarthquakeViewModel: ViewModel() {

    private val _earthquakeData = MutableLiveData<List<EarthquakeFeature>>()
    val earthquakeData: LiveData<List<EarthquakeFeature>> get() = _earthquakeData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error


    fun getEarthquakeData(format: String, startTime: String, endTime: String, minMagnitude: Double) {
        viewModelScope.launch {
            try {
                val response =
                    EarthquakeRepository.getEarthquakeData(format, startTime, endTime, minMagnitude)

                _earthquakeData.postValue(response.features)
            } catch (e: HttpException) {
                _error.value = "HTTP error: ${e.message()}"
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            }
        }
    }
}
