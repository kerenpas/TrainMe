package com.example.trainme.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trainme.domain.models.User
import com.example.trainme.presentation.components.EmailTextField
import com.example.trainme.presentation.components.PhoneTextField
import com.example.trainme.presentation.viewmodels.RegistrationViewModel
import org.koin.androidx.compose.koinViewModel

// presentation/screens/RegistrationScreen.kt
@Composable
fun RegistrationScreen(
    registrationviewModel: RegistrationViewModel = koinViewModel(),
    onNavigateBack: () -> Unit,
    onRegistrationSuccess: (User) -> Unit
) {
    val registrationState by registrationviewModel.registrationState.collectAsState()

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.run {
            fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Register",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        EmailTextField(
            email = email,
            onEmailChanged = { email = it },
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = dateOfBirth,
            onValueChange = { dateOfBirth = it },
            label = { Text("Date of Birth (YYYY-MM-DD)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("City") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        PhoneTextField(
            phone = phone,
            onPhoneChanged = { phone = it },
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        Button(
            onClick = {
                registrationviewModel.register(
                    firstName, lastName, email, dateOfBirth, city, phone, address
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Register")
        }

        TextButton(
            onClick = onNavigateBack,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Already have an account? Log in")
        }

        when (registrationState) {
            is RegistrationViewModel.RegistrationState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
            }
            is RegistrationViewModel.RegistrationState.Error -> {
                Text(
                    text = (registrationState as RegistrationViewModel.RegistrationState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            is RegistrationViewModel.RegistrationState.Success -> {
                LaunchedEffect(Unit) {
                    onRegistrationSuccess((registrationState as RegistrationViewModel.RegistrationState.Success).user)
                }
            }
            else -> {}
        }
    }
}