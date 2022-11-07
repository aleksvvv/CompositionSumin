package com.example.compositionsumin.presentation

import android.app.Application
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.compositionsumin.R
import com.example.compositionsumin.data.GameRepositoryImpl
import com.example.compositionsumin.domain.entity.GameResult
import com.example.compositionsumin.domain.entity.GameSettings
import com.example.compositionsumin.domain.entity.Level
import com.example.compositionsumin.domain.entity.Question
import com.example.compositionsumin.domain.usecases.GenerateQuestionUseCase
import com.example.compositionsumin.domain.usecases.GetGameSettingsUseCase

class GameViewModel(
    private val application: Application,
    private val level: Level
    ) : ViewModel() {
    private lateinit var gameSettings: GameSettings
//    private lateinit var level: Level
//
//    private val context = application

    private val repository = GameRepositoryImpl

    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private var timer: CountDownTimer? = null

    private var _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers

    private val _progressAnswer = MutableLiveData<String>()
    val progressAnswer: LiveData<String>
        get() = _progressAnswer

    private val _enoughCountOfRightAnswers = MutableLiveData<Boolean>()
    val enoughCountOfRightAnswers: LiveData<Boolean>
        get() = _enoughCountOfRightAnswers

    private val _enoughPercentOfRightAnswers = MutableLiveData<Boolean>()
    val enoughPercentOfRightAnswers: LiveData<Boolean>
        get() = _enoughPercentOfRightAnswers

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private var _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private var countOfRightAnswers = 0
    private var countOfQuestion = 0
    init {
        startGame()
    }

   private fun startGame() {
        getGameSettings()
        startTimer()
        generateQuestion()
        updateProgress()
    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generateQuestion()
    }

    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswers.value = percent
        _progressAnswer.value = String.format(
            application.resources.getString(R.string.progress_answers),
            countOfRightAnswers,
            gameSettings.minCountOfRightAnswers
        )
        _enoughCountOfRightAnswers.value =
            countOfRightAnswers >= gameSettings.minCountOfRightAnswers
        _enoughPercentOfRightAnswers.value = percent >= gameSettings.minPercentOfRightAnswers
    }

    private fun calculatePercentOfRightAnswers(): Int {
        if (countOfQuestion ==0){
            return 0
        }
        val res =((countOfRightAnswers / countOfQuestion.toDouble()) * 100).toInt()

        return res
    }

    private fun checkAnswer(number: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (number == rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestion++
    }

    private fun getGameSettings() {
//        this.level = level
        this.gameSettings = getGameSettingsUseCase(level)
        _minPercent.value = gameSettings.minPercentOfRightAnswers
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            gameSettings.gameTimeInSeconds * MILLISECOND_COUNT, MILLISECOND_COUNT
        ) {
            override fun onTick(milliSecondForFinished: Long) {
                _formattedTime.value = formTime(milliSecondForFinished)
            }
            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    private fun generateQuestion() {
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    private fun formTime(milliSecondForFinished: Long): String {
        val second = milliSecondForFinished / MILLISECOND_COUNT
        val minutes = second / SECOND_IN_MILLISECOND
        val leftSecond = second - (minutes * SECOND_IN_MILLISECOND)
        return String.format("%02d:%02d", minutes, leftSecond)
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            (enoughCountOfRightAnswers.value == true && enoughPercentOfRightAnswers.value == true),
            countOfRightAnswers,
            countOfQuestion,
            gameSettings
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object {
        const val MILLISECOND_COUNT = 1000L
        const val SECOND_IN_MILLISECOND = 60
    }

}