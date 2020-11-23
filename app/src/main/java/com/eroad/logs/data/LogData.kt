package com.eroad.logs.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LogData(
    @Expose @SerializedName("start")
    var start: Long?,
    @Expose @SerializedName("end")
    var end: Long?,
    @Expose @SerializedName("sensorData")
    var sensorData: ArrayList<SensorData>?

)