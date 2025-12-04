package com.example.comidas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AuthScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) { // Use a Column to make buttons the same width
        Column(modifier = Modifier.fillMaxWidth(0.8f).height(IntrinsicSize.Min)) {
            Button(
                onClick = { navController.navigate(AppScreen.Login.route) },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text("Iniciar sesión",
                    modifier = Modifier.padding(8.dp)
                )
            }
            Button(
                onClick = { navController.navigate(AppScreen.Register.route) },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text("Registrar nueva cuenta", modifier = Modifier.padding(8.dp))
            }
        }
    }
}