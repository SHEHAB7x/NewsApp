package com.example.newsapp.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.model.Country
import com.example.newsapp.domain.model.Language
import com.example.newsapp.domain.model.TextSize
import com.example.newsapp.utils.UserPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesManager: UserPreferencesManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    init{
        loadPreferences()
    }

    private fun loadPreferences() {
        viewModelScope.launch {
            combine(
                preferencesManager.isDarkModeEnabled,
                preferencesManager.isNotificationsEnabled,
                preferencesManager.selectedLanguage,
                preferencesManager.selectedCountry,
                preferencesManager.selectedTextSize
            ){ darkMode, notifications, language, country, textSize ->
                SettingsUiState(
                    isDarkMode             = darkMode,
                    isNotificationsEnabled = notifications,
                    selectedLanguage       = language,
                    selectedCountry        = country,
                    selectedTextSize       = textSize
                )
            }.collect { state ->
                _uiState.update {
                    it.copy(
                        isDarkMode             = state.isDarkMode,
                        isNotificationsEnabled = state.isNotificationsEnabled,
                        selectedLanguage       = state.selectedLanguage,
                        selectedCountry        = state.selectedCountry,
                        selectedTextSize       = state.selectedTextSize
                    )
                }
            }
        }
    }

    fun selectedTextSize(textSize: TextSize) {
        viewModelScope.launch {
            preferencesManager.setTextSize(textSize)
        }
    }

    fun showTextSizeDialog() {
        _uiState.update { it.copy(showTextSizeDialog = true) }
    }

    fun toggleDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            preferencesManager.setDarkModeEnabled(enabled)
        }
    }

    fun toggleNotifications(enabled: Boolean) {
        viewModelScope.launch {
            preferencesManager.setNotificationsEnabled(enabled)
        }
    }

    fun selectLanguage(language: Language) {
        viewModelScope.launch {
            preferencesManager.setLanguage(language)
            _uiState.update { it.copy(showLanguageDialog = false) }
        }
    }

    fun selectCountry(country: Country) {
        viewModelScope.launch {
            preferencesManager.setCountry(country)
            _uiState.update { it.copy(showCountryDialog = false) }
        }
    }

    fun showLanguageDialog() {
        _uiState.update { it.copy(showLanguageDialog = true) }
    }

    fun showCountryDialog() {
        _uiState.update { it.copy(showCountryDialog = true) }
    }

    fun dismissDialogs() {
        _uiState.update {
            it.copy(showLanguageDialog = false, showCountryDialog = false, showTextSizeDialog = false)
        }
    }
}