package com.test.weatherforecastapp.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Coordination (
  @SerializedName("lon")
  var lon: Double? = null,
  @SerializedName("lat")
  var lat: Double? = null
)