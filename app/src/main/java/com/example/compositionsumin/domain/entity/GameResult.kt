package com.example.compositionsumin.domain.entity

data class GameResult(
    val winner: Boolean,
    val countOfAnswers: Int,
    val countOfQuestions: Int,
    val gameSettings: GameSettings
)