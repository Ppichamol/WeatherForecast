package com.test.weatherforecastapp.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class GetGeoResponse (
  @SerializedName("name")
  var name: String? = null,
  @SerializedName("lat")
  var lat: Double? = null,
  @SerializedName("lon")
  var lon: Double? = null,
  @SerializedName("country")
  var country: String? = null
)