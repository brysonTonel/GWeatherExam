package com.bryson.gweather

import com.bryson.gweather.domain.model.Weather
import com.bryson.gweather.domain.usecase.GetWeatherHistoryUseCase
import com.bryson.gweather.presentation.UiState
import com.bryson.gweather.presentation.viewmodel.WeatherHistoryViewModel
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


@ExperimentalCoroutinesApi
class WeatherHistoryViewModelTest {

    private lateinit var getWeatherHistory: GetWeatherHistoryUseCase
    private lateinit var viewModel: WeatherHistoryViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getWeatherHistory = mock()
        viewModel = WeatherHistoryViewModel(getWeatherHistory)
    }

    @Test
    fun `loadHistory emits Success with list`() = runTest {
        val weatherList = listOf(
            Weather(
                city = "City1",
                country = "Country1",
                temperature = 20.0,
                sunrise = 1234567890,
                sunset = 1234567890,
                condition = "Clear",
                icon = "01d",
                timestamp = System.currentTimeMillis()
            ),
            Weather(
                city = "City2",
                country = "Country2",
                temperature = 22.0,
                sunrise = 1234567891,
                sunset = 1234567891,
                condition = "Clouds",
                icon = "02d",
                timestamp = System.currentTimeMillis()
            )
        )

        whenever(getWeatherHistory.invoke()).thenReturn(flowOf(weatherList))

        viewModel = WeatherHistoryViewModel(getWeatherHistory)

        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state is UiState.Success)
        assertEquals(weatherList, (state as UiState.Success).data)
    }

    @Test
    fun `loadHistory emits Error on exception`() = runTest {
        val exception = RuntimeException("Database error")
        whenever(getWeatherHistory.invoke()).thenThrow(exception)

        viewModel = WeatherHistoryViewModel(getWeatherHistory)
        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state is UiState.Error)
        assertEquals("Database error", (state as UiState.Error).message)
    }
}
