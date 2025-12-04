package com.example.comidas.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.comidas.data.LoginViewModel

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    val context = LocalContext.current
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
            text = "Inicio de Sesión",
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
            loginViewModel.getUserByName(username) { user ->
                if (user != null && user.password == password) {
                    onLoginSuccess()
                } else if (user==null){
                    message = "Usuario no encontrado"
                }
                else{
                    message = "Contraseña incorrecta"
                }
            }
        }) {
            Text("Iniciar")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = message)
    }
}