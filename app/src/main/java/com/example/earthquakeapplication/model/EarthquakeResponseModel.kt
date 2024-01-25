package com.example.earthquakeapplication.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class EarthquakeResponseModel(
    val type: String,
    val metadata: Metadata,
    val features: List<EarthquakeFeature>,
    val bbox: List<Double>
)

data class Metadata(
    val generated: Long,
    val url: String,
    val title: String,
    val status: Int,
    val api: String,
    val count: Int
)

data class EarthquakeFeature(
    val type: String,
    val properties: EarthquakeProperties,
    val geometry: EarthquakeGeometry,
    val id: String
)

data class EarthquakeProperties(
    val mag: Double, //>=7.5
    val place: String,
    val time: Long,
    val updated: Long,
//    val tz: Any?, // null in api, not sure the type
    val url: String,
    val detail: String,
    val felt: Int,
    val cdi: Double,
    val mmi: Double,
    val alert: String,
    val status: String,
    val tsunami: Int,
    val sig: Int,
    val net: String,
    val code: String,
    val ids: String,
    val sources: String,
    val types: String,
    val nst: Int,
    val dmin: Double,
    val rms: Double,
    val gap: Int,
    @SerializedName("magType")
    val magType: String,
    val title: String
)

@Parcelize
data class EarthquakeGeometry(
    val type: String,
    val coordinates: List<Double> // coordinates[0]: longitude, coordinates[1]: latitude
): Parcelable
