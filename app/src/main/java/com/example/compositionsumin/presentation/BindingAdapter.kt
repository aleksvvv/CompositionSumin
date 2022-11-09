package com.example.compositionsumin.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.compositionsumin.R
import com.example.compositionsumin.domain.entity.GameResult

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView, count:Int){
    textView.text = String.format(textView.context.getString(R.string.required_score)
        , count)
}
@BindingAdapter("requiredPercent")
fun bindRequiredPercent(textView: TextView,count: Int){
    textView.text = String.format(
        textView.context.getString(R.string.required_percentage),
        count
    )
}
@BindingAdapter("scoreAnswers")
fun bindScoreAnswers(textView: TextView,count: Int){
    textView.text = kotlin.String.format(
        textView.context.getString(R.string.score_answers),
    count
)}
@BindingAdapter("smileResId")
fun bindSmileResId(imageView: ImageView,winner:Boolean){
    imageView.setImageResource(getSmileResId(winner))
}
private fun getSmileResId(winner: Boolean): Int {
    return if (winner) {
        R.drawable.ic_smile
    } else {
        R.drawable.ic_sad
    }
}
@BindingAdapter("scorePercentage")
fun bindscorePercentage(textView: TextView, gameResult: GameResult){
    textView.text = String.format(
        textView.context.getString(R.string.score_percentage),
        getPercentOfRightAnswers(gameResult)
    )
}
private fun getPercentOfRightAnswers(gameResult:GameResult) = with(gameResult) {
    if (countOfQuestions == 0) {
        0
    } else {
        ((countOfAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }
}