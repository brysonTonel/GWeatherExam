package com.bryson.gweather

import com.bryson.gweather.data.repository.AuthRepository
import com.bryson.gweather.presentation.AuthState
import com.bryson.gweather.presentation.viewmodel.AuthViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any

@ExperimentalCoroutinesApi
class AuthViewModelTest {

    private lateinit var repository: AuthRepository
    private lateinit var viewModel: AuthViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        repository = mock(AuthRepository::class.java)
        viewModel = AuthViewModel(repository)
    }

    @Test
    fun `register success updates authState with Success`() = runTest {
        `when`(repository.register(any(), any())).thenReturn(Result.success(Unit))

        viewModel.register("test@gmail.com", "123456")

        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state is AuthState.Success)
    }

    @Test
    fun `register failure updates state to Error`() = runTest {
        val exception = Exception("Registration failed")
        `when`(repository.register(any(), any())).thenReturn(Result.failure(exception))

        viewModel.register("test@example.com", "password123")

        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state is AuthState.Error)
        assertEquals("Registration failed", (state as AuthState.Error).message)
    }

    @Test
    fun `login success updates state to Success`() = runTest {
        `when`(repository.login(any(), any())).thenReturn(Result.success(Unit))

        viewModel.login("test@gmail.com", "123456")

        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state is AuthState.Success)
    }

    @Test
    fun `login failure updates state to Error`() = runTest {
        val exception = Exception("Login failed")
        `when`(repository.login(any(), any())).thenReturn(Result.failure(exception))

        viewModel.login("test@example.com", "password123")

        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state is AuthState.Error)
        assertEquals("Login failed", (state as AuthState.Error).message)
    }

}

@ExperimentalCoroutinesApi
class MainDispatcherRule(
    val dispatcher: TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description?) {
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        Dispatchers.resetMain()
    }
}
