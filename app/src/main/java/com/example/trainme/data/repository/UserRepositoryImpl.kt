package com.example.trainme.data.repository

import com.example.trainme.data.api.ApiService
import com.example.trainme.data.models.LoginRequest
import com.example.trainme.data.models.UserRegistrationRequest
import com.example.trainme.data.models.UserResponse
import com.example.trainme.domain.models.User
import com.example.trainme.domain.models.UserRegistration
import com.example.trainme.domain.repository.IUserRepository

// data/repository/UserRepositoryImpl.kt
class UserRepositoryImpl(private val apiService: ApiService) : IUserRepository {
    override suspend fun registerUser(user: UserRegistration): Result<User> {
        return try {
            val request = UserRegistrationRequest(
                first_name = user.firstName,
                last_name = user.lastName,
                email = user.email,
                date_of_birth = user.dateOfBirth,
                city = user.city ?: "",
                phone = user.phone,
                address = user.address ?: ""
            )

            val response = apiService.registerUser(request)
            if (response.isSuccess && response.data != null) {
                Result.success(response.data.toDomainModel())
            } else {
                Result.failure(Exception(response.message ?: "Registration failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun loginUser(email: String, phone: String): Result<User> {
        return try {
            val request = LoginRequest(email, phone)
            val response = apiService.loginUser(request)

            if (response.isSuccess && response.data != null) {
                Result.success(response.data.toDomainModel())
            } else {
                Result.failure(Exception(response.message ?: "Login failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserDetails(userId: Int): Result<User> {
        return try {
            val response = apiService.getUserDetails(userId)

            if (response.isSuccess && response.data != null) {
                Result.success(response.data.toDomainModel())
            } else {
                Result.failure(Exception(response.message ?: "Failed to get user details"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun UserResponse.toDomainModel(): User {
        return User(
            id = id,
            firstName = first_name,
            lastName = last_name,
            email = email,
            dateOfBirth = date_of_birth ?: "",
            city = city,
            phone = phone,
            address = address
        )
    }
}