package com.bryson.gweather.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bryson.gweather.data.repository.AuthRepository
import com.bryson.gweather.presentation.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
    val state: StateFlow<AuthState> = _state

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _state.value = AuthState.Loading
            val result = repo.register(email, password)
            _state.value = result.fold(
                onSuccess = { AuthState.Success },
                onFailure = { AuthState.Error(it.localizedMessage ?: "Registration failed") }
            )
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.value = AuthState.Loading
            val result = repo.login(email, password)
            _state.value = result.fold(
                onSuccess = { AuthState.Success },
                onFailure = { AuthState.Error(it.localizedMessage ?: "Login failed") }
            )
        }
    }
}
