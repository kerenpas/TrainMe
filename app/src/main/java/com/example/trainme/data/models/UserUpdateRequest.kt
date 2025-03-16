package com.example.trainme.data.models

data class UserUpdateRequest(
    val first_name: String? = null,
    val last_name: String? = null,
    val city: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val date_of_birth: String? = null
)
