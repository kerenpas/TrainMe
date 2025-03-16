// com/example/trainme/presentation/viewmodels/HomeViewModel.kt
package com.example.trainme.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trainme.domain.models.User
import com.example.trainme.domain.usecases.GetUserDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val userId: Int
) : ViewModel() {

    private val _userState = MutableStateFlow(UserState())
    val userState: StateFlow<UserState> = _userState.asStateFlow()

    init {
        loadUserDetails(userId)
    }

    fun loadUserDetails(userId: Int) {
        viewModelScope.launch {
            try {
                _userState.value = UserState(isLoading = true)
                val result = getUserDetailsUseCase(userId)

                result.fold(
                    onSuccess = { user ->
                        _userState.value = UserState(
                            user = user,
                            isLoading = false
                        )
                    },
                    onFailure = { e ->
                        _userState.value = UserState(
                            error = e.message ?: "Failed to load user details",
                            isLoading = false
                        )
                    }
                )
            } catch (e: Exception) {
                _userState.value = UserState(
                    error = e.message ?: "An unexpected error occurred",
                    isLoading = false
                )
            }
        }
    }

    data class UserState(
        val user: User? = null,
        val isLoading: Boolean = false,
        val error: String = ""
    )
}