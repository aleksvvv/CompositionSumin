package com.example.compositionsumin.domain.usecases

import com.example.compositionsumin.domain.entity.GameSettings
import com.example.compositionsumin.domain.entity.Level
import com.example.compositionsumin.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {

    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}