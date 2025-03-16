package com.example.trainme.domain.repository

import com.example.trainme.domain.models.User
import com.example.trainme.domain.models.UserRegistration

interface IUserRepository {
    suspend fun registerUser(user: UserRegistration): Result<User>
    suspend fun loginUser(email: String, phone: String): Result<User>
    suspend fun getUserDetails(userId: Int): Result<User>
}