package com.bryson.gweather

import com.bryson.gweather.domain.model.Weather
import com.bryson.gweather.domain.usecase.GetCurrentWeatherUseCase
import com.bryson.gweather.domain.usecase.GetWeatherHistoryUseCase
import com.bryson.gweather.presentation.UiState
import com.bryson.gweather.presentation.viewmodel.CurrentWeatherViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class CurrentWeatherViewModelTest {

    private lateinit var fetchCurrentWeather: GetCurrentWeatherUseCase
    private lateinit var getWeatherHistory: GetWeatherHistoryUseCase
    private lateinit var viewModel: CurrentWeatherViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fetchCurrentWeather = mock()
        getWeatherHistory = mock()
        viewModel = CurrentWeatherViewModel(fetchCurrentWeather, getWeatherHistory)
    }

    @Test
    fun `loadCurrentWeather success updates state to Success`() = runTest {
        val weather = Weather(
            city = "Test City",
            country = "Test Country",
            temperature = 25.0,
            sunrise = 1234567890,
            sunset = 1234567890,
            condition = "Clear",
            icon = "01d",
            timestamp = System.currentTimeMillis()
        )

        whenever(fetchCurrentWeather.invoke(any(), any())).thenReturn(Unit)

        whenever(getWeatherHistory.invoke()).thenReturn(flow { emit(listOf(weather)) })

        viewModel.loadCurrentWeather(0.0, 0.0)

        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state is UiState.Success)
        assertEquals(weather, (state as UiState.Success).data)
    }

    @Test
    fun `loadCurrentWeather failure updates state to Error`() = runTest {
        val exception = RuntimeException("Network error")


        whenever(fetchCurrentWeather.invoke(any(), any())).thenThrow(exception)

        viewModel.loadCurrentWeather(0.0, 0.0)

        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state is UiState.Error)
        assertEquals("Network error", (state as UiState.Error).message)
    }
}