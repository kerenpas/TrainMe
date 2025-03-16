package com.example.trainme.domain.models

data class User(
    val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val email: String,
    val dateOfBirth: String,
    val city: String?,
    val phone: String,
    val address: String?
)
