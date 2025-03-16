package com.example.trainme.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import com.example.trainme.domain.models.User
import com.example.trainme.presentation.components.EmailTextField
import com.example.trainme.presentation.components.PhoneTextField
import com.example.trainme.presentation.viewmodels.LoginViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material3.ButtonDefaults
import com.example.trainme.ui.theme.Primary
import com.example.trainme.ui.theme.TextOnDark
import com.example.trainme.ui.theme.Accent
import androidx.compose.material3.ButtonDefaults.textButtonColors


// presentation/screens/LoginScreen.kt
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = koinViewModel(),
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: (User) -> Unit
) {
    val loginState by loginViewModel.loginState.collectAsState()

    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        EmailTextField(
            email = email,
            onEmailChanged = { email = it },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        PhoneTextField(
            phone = phone,
            onPhoneChanged = { phone = it },
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Button(
            onClick = { loginViewModel.login(email, phone) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,  // Use your gold color
                contentColor = TextOnDark  // Text will be white
            )
        ) {
            Text("Login")
        }

        TextButton(
            onClick = onNavigateToRegister,
            modifier = Modifier.padding(top = 16.dp),
            colors = textButtonColors(
                contentColor = Accent  // Use your accent gold
            )
        ) {
            Text("Don't have an account? Register")
        }

        when (loginState) {
            is LoginViewModel.LoginState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
            }
            is LoginViewModel.LoginState.Error -> {
                Text(
                    text = (loginState as LoginViewModel.LoginState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            is LoginViewModel.LoginState.Success -> {
                LaunchedEffect(Unit) {
                    onLoginSuccess((loginState as LoginViewModel.LoginState.Success).user)
                }
            }
            else -> {}
        }
    }
}

