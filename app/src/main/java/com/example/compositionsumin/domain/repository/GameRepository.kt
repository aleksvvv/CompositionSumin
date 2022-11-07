package com.example.compositionsumin.domain.repository

import com.example.compositionsumin.domain.entity.GameSettings
import com.example.compositionsumin.domain.entity.Level
import com.example.compositionsumin.domain.entity.Question

interface GameRepository {

    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question

    fun getGameSettings(level: Level): GameSettings
}