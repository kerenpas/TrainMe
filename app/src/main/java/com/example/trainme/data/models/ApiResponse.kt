package com.example.trainme.data.models

data class ApiResponse<T>(
    val data: T? = null,
    val message: String? = null,
    val isSuccess: Boolean = true
)
