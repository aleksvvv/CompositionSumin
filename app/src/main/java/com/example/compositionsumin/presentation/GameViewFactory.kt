package com.example.compositionsumin.presentation

import android.app.Application
import android.view.View
import android.widget.ViewSwitcher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compositionsumin.domain.entity.Level

class GameViewFactory(
    private val application: Application,
    private val level: Level
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)){
        return GameViewModel(application, level) as T}
        throw RuntimeException("View Model $modelClass is unknown ")
    }
}
