package com.bening.luckywheel.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataWheel (
    var id: Int,
    var nama: String
) : Parcelable