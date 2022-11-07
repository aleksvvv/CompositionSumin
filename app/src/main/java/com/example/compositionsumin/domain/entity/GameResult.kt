package com.example.compositionsumin.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class GameResult(
    val winner: Boolean,
    val countOfAnswers: Int,
    val countOfQuestions: Int,
    val gameSettings: GameSettings
):Parcelable