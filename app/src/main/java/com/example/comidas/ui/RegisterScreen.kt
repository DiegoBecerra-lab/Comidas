package com.example.comidas.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.comidas.data.LoginViewModel
import com.example.comidas.data.User

@Composable
fun RegisterScreen(onRegisterSuccess: () -> Unit) {
    val loginViewModel: LoginViewModel = viewModel()
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Registrar Cuenta",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Nombre de Usuario") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (username.isNotBlank() && password.isNotBlank()) {
                val user = User(name = username, password = password)
                loginViewModel.registerUser(user) { success ->
                    if (success) {
                        onRegisterSuccess()
                    } else {
                        message = "El nombre de usuario ya existe"
                    }
                }
            } else {
                message = "Ingrese un nombre de usuario y contraseña"
            }
        }) {
            Text("Registrar")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = message)
    }
}