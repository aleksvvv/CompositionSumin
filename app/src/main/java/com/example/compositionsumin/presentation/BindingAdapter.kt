package com.example.compositionsumin.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.compositionsumin.R
import com.example.compositionsumin.domain.entity.GameResult

interface OnOptionClickListener{
    fun onOptionClick(option:Int)
}
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
@BindingAdapter("enoughCount")
fun bindEnoughCount(textView: TextView, percent:Boolean){
        textView.setTextColor(getColorByState(textView.context,percent))
}

@BindingAdapter("enoughPercent")
fun bindEnoughPercentOfRightAnswers(textView: ProgressBar, percent:Boolean){
    val color =getColorByState(textView.context, percent)
    textView.progressTintList = ColorStateList.valueOf(color)
}
private fun getColorByState(context: Context, goodState: Boolean): Int {
    val colorResId = if (goodState) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return ContextCompat.getColor(context, colorResId)
}
@BindingAdapter("numberAsText")
fun bindNumberAsText(textView: TextView, number: Int){
    textView.text = number.toString()
}
@BindingAdapter("onOptionClickListener")
fun bindOnOptionClickListener(textView: TextView,clickListener: OnOptionClickListener){
textView.setOnClickListener{
    clickListener.onOptionClick(textView.text.toString().toInt())
}
}
