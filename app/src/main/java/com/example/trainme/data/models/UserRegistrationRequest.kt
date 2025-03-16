package com.example.trainme.data.models

data class UserRegistrationRequest(
    val first_name: String,
    val last_name: String,
    val email: String,
    val date_of_birth: String,
    val city: String,
    val phone: String,
    val address: String
)
