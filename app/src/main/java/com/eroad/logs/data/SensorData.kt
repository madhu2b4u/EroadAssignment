package com.eroad.logs.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SensorData(
    @Expose @SerializedName("t_sec")
    var tSec: Long?,
    @Expose @SerializedName("x_acc")
    var xAcc: Float?,
    @Expose @SerializedName("y_acc")
    var yAcc: Float?,
    @Expose @SerializedName("z_acc")
    var zAcc: Float?
)