package com.playground.blueskiesweather.vm

import com.playground.blueskiesweather.repository.ForecastRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ForecastViewModel @Inject constructor(val forecastRepository: ForecastRepository): ViewModel() {

    fun getForecast() {
        viewModelScope.launch {
            forecastRepository.getPoints(47.085476, -92.664109)
        }
    }
}