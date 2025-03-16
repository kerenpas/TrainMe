// com/example/trainme/di/AppModule.kt
package com.example.trainme.di

import com.example.trainme.data.api.RetrofitClient
import com.example.trainme.data.repository.UserRepositoryImpl
import com.example.trainme.domain.repository.ILessonRepository
import com.example.trainme.domain.repository.IUserRepository
import com.example.trainme.domain.usecases.GetUserDetailsUseCase
import com.example.trainme.domain.usecases.LoginUserUseCase
import com.example.trainme.domain.usecases.RegisterUserUseCase
import com.example.trainme.presentation.viewmodels.HomeViewModel
import com.example.trainme.presentation.viewmodels.LoginViewModel
import com.example.trainme.presentation.viewmodels.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.example.trainme.data.repository.LessonRepository
import com.example.trainme.domain.usecases.GetUpcomingLessonsUseCase
import com.example.trainme.domain.usecases.RegisterForLessonUseCase

import com.example.trainme.presentation.viewmodel.UpcomingLessonsViewModel


val appModule = module {
    // API
    single { RetrofitClient.createApiService() }

    // Repositories
    single<IUserRepository> { UserRepositoryImpl(get()) }
    single<ILessonRepository> { LessonRepository(get()) }

    // Use Cases
    factory { RegisterUserUseCase(get()) }
    factory { LoginUserUseCase(get()) }
    factory { GetUserDetailsUseCase(get()) }
    factory { GetUpcomingLessonsUseCase(get()) }
    factory { RegisterForLessonUseCase(get()) }


    // ViewModels
    viewModel { LoginViewModel(get()) }
    viewModel { RegistrationViewModel(get()) }
    viewModel { params -> HomeViewModel(get(), params.get()) }
    viewModel { UpcomingLessonsViewModel(get(), get()) }
}