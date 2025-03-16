package com.example.trainme.domain.usecases

import com.example.trainme.domain.models.User
import com.example.trainme.domain.models.UserRegistration
import com.example.trainme.domain.repository.IUserRepository

// domain/usecases/RegisterUserUseCase.kt
class RegisterUserUseCase(private val userRepository: IUserRepository) {
    suspend operator fun invoke(userRegistration: UserRegistration): Result<User> {
        return userRepository.registerUser(userRegistration)
    }
}

// domain/usecases/LoginUserUseCase.kt
class LoginUserUseCase(private val userRepository: IUserRepository) {
    suspend operator fun invoke(email: String, phone: String): Result<User> {
        return userRepository.loginUser(email, phone)
    }
}

// domain/usecases/GetUserDetailsUseCase.kt
class GetUserDetailsUseCase(private val userRepository: IUserRepository) {
    suspend operator fun invoke(userId: Int): Result<User> {
        return userRepository.getUserDetails(userId)
    }
}