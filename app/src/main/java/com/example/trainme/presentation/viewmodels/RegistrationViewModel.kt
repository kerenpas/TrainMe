package com.example.trainme.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trainme.domain.models.User
import com.example.trainme.domain.models.UserRegistration
import com.example.trainme.domain.usecases.RegisterUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// presentation/viewmodels/RegistrationViewModel.kt
class RegistrationViewModel(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {
    private val _registrationState = MutableStateFlow<RegistrationState>(RegistrationState.Initial)
    val registrationState: StateFlow<RegistrationState> = _registrationState

    fun register(
        firstName: String,
        lastName: String,
        email: String,
        dateOfBirth: String,
        city: String,
        phone: String,
        address: String
    ) {
        viewModelScope.launch {
            _registrationState.value = RegistrationState.Loading

            val userRegistration = UserRegistration(
                firstName = firstName,
                lastName = lastName,
                email = email,
                dateOfBirth = dateOfBirth,
                city = city,
                phone = phone,
                address = address
            )

            val result = registerUserUseCase(userRegistration)
            _registrationState.value = result.fold(
                onSuccess = { RegistrationState.Success(it) },
                onFailure = { RegistrationState.Error(it.message ?: "Unknown error") }
            )
        }
    }

    sealed class RegistrationState {
        object Initial : RegistrationState()
        object Loading : RegistrationState()
        data class Success(val user: User) : RegistrationState()
        data class Error(val message: String) : RegistrationState()
    }
}