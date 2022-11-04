package com.example.compositionsumin.data

import com.example.compositionsumin.domain.entity.GameSettings
import com.example.compositionsumin.domain.entity.Level
import com.example.compositionsumin.domain.entity.Question
import com.example.compositionsumin.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {
    const val MIN_SUM_VALUE = 2
    const val MIN_ANSWER_VALUE = 1

    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum)
        val options = hashSetOf<Int>()
        val rightAnswer = sum - visibleNumber
        options.add(rightAnswer)
        val from = max(rightAnswer - countOfOptions, MIN_ANSWER_VALUE)
        val to = min(maxSumValue, rightAnswer + countOfOptions)
        while (options.size < countOfOptions) {
            options.add(Random.nextInt(from, to))
        }
        return Question(sum, visibleNumber, options.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when(level){
            Level.TEST->{
                GameSettings(
                    10,
                    3,
                    50,
                    8
                )
            }
            Level.EASY->{
                GameSettings(
                    10,
                    10,
                    70,
                    60
                )
            }
            Level.NORMAL->{
                GameSettings(
                    20,
                    20,
                    80,
                    40
                )
            }
            Level.HARD->{
                GameSettings(
                    30,
                    30,
                    90,
                    40
                )
            }
        }
    }
}