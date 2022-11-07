package com.example.compositionsumin.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class Level:Parcelable {
    TEST, EASY, NORMAL, HARD
}