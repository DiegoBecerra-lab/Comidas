package com.example.comidas.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.comidas.data.LoginViewModel

@Composable
fun UsersScreen(onCloseSession: () -> Unit) {
    val loginViewModel: LoginViewModel = viewModel()
    val users by loginViewModel.allUsers.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Cuentas Registradas",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { loginViewModel.clearAllUsers() }) {
                Text("Borrar Cuentas")
            }
            Button(onClick = onCloseSession) {
                Text("Cerrar Sesión")
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {
            items(users) { user ->
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = "Nombre de usuario: ${user.name}")
                    Text(text = "Contraseña: ${user.password}")
                }
            }
        }
    }
}