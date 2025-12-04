package com.example.comidas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.comidas.ui.LoginScreen
import com.example.comidas.ui.RegisterScreen
import com.example.comidas.ui.UsersScreen
import com.example.comidas.ui.theme.ComidasTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComidasTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = AppScreen.Auth.route) {
                    composable(AppScreen.Auth.route) {
                        AuthScreen(navController = navController)
                    }

                    composable(AppScreen.Login.route) {
                        LoginScreen(
                            onLoginSuccess = {
                                navController.navigate(AppScreen.Main.route) {
                                    popUpTo(AppScreen.Auth.route) { inclusive = true }
                                }
                            }
                        )
                    }
                    composable(AppScreen.Register.route) {
                        RegisterScreen(onRegisterSuccess = {
                            navController.navigate(AppScreen.Login.route) {
                                popUpTo(AppScreen.Auth.route) { inclusive = true }
                            }
                        })
                    }

                    composable(AppScreen.Main.route) {
                        AppConMenuSuperior(onCloseSession = {
                            navController.navigate(AppScreen.Auth.route) {
                                popUpTo(AppScreen.Main.route) { inclusive = true }
                            }
                        })
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppConMenuSuperior(onCloseSession: () -> Unit) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerSheet(navController, drawerState)
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(textoTitulo(navController)) },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open() else drawerState.close()
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú")
                        }
                    }
                )
            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = "inicio",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                composable("inicio") { PantallaInicio() }
                composable("mexico") { PantallaMexico() }
                composable("japon") { PantallaJapon() }
                composable("italia") { PantallaItalia() }
                composable("users") { UsersScreen(onCloseSession = onCloseSession) }
            }
        }
    }
}
@Composable
fun DrawerSheet(navController: NavController, drawerState: DrawerState) {
    val scope = rememberCoroutineScope()
    ModalDrawerSheet {
        Text("Menú de navegación", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(16.dp))
        NavigationDrawerItem(
            label = { Text("Inicio") },
            selected = false,
            onClick = {
                navController.navigate("inicio")
                scope.launch { drawerState.close() }
            }
        )
        NavigationDrawerItem(
            label = { Text("Mexico") },
            selected = false,
            onClick = {
                navController.navigate("mexico")
                scope.launch { drawerState.close() }
            }
        )
        NavigationDrawerItem(
            label = { Text("Japon") },
            selected = false,
            onClick = {
                navController.navigate("japon")
                scope.launch { drawerState.close() }
            }
        )
        NavigationDrawerItem(
            label = { Text("Italia") },
            selected = false,
            onClick = {
                navController.navigate("italia")
                scope.launch { drawerState.close() }
            }
        )
        NavigationDrawerItem(
            label = { Text("Cuentas") },
            selected = false,
            onClick = {
                navController.navigate("users")
                scope.launch { drawerState.close() }
            }
        )
    }
}

@Composable
fun textoTitulo(navController: NavController): String {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    return when (currentRoute) {
        "inicio" -> "Inicio"
        "mexico" -> "Mexico"
        "japon" -> "Japon"
        "italia" -> "Italia"
        "users" -> "Users"
        else -> "Mi App"
    }
}
@Composable
fun PantallaInicio() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

        Text(
            text = "Comidas alrededor del mundo",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "La comida alrededor del mundo refleja la " +
                    "diversidad cultural y las tradiciones de cada país. " +
                    "Cada región tiene sabores únicos:" +
                    "desde la pasta italiana y el sushi japonés, " +
                    "hasta los curris de la India o los tacos mexicanos." +
                    "La gastronomía une a las personas y muestra la creatividad " +
                    "de cada cultura a través de sus ingredientes y formas de cocinar.",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 16.sp,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Image(
            painterResource(id = R.drawable.mapcom),
            contentDescription = "Mapa",
            modifier = Modifier
                .size(300.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, Color.Gray, RoundedCornerShape(16.dp))
        )
    }

}

@Composable
fun PantallaMexico() {

    val imagenes = listOf(
        Pair(R.drawable.chilas, "Chilaquiles"),
        Pair(R.drawable.garnacha, "Garnachas"),
        Pair(R.drawable.mole, "Mole"),
        Pair(R.drawable.pozole, "Pozole"),
        Pair(R.drawable.tacos, "Tacos")
    )

    var indiceActual by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Text(
            text = "México",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 50.sp,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(15.dp))
        // Imagen actual
        Image(
            painter = painterResource(id = imagenes[indiceActual].first),
            contentDescription = imagenes[indiceActual].second,
            modifier = Modifier
                .size(250.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, Color.Gray, RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Texto descriptivo de la imagen
        Text(
            text = imagenes[indiceActual].second,
            fontSize = 30.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Botones para navegar entre imágenes
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    if (indiceActual > 0) indiceActual--
                    else indiceActual = imagenes.size - 1 // vuelve al final si está al inicio
                }
            ) {
                Text("Anterior")
            }

            Button(
                onClick = {
                    if (indiceActual < imagenes.size - 1) indiceActual++
                    else indiceActual = 0 // vuelve al inicio si llega al final
                }
            ) {
                Text("Siguiente")
            }
        }
        Text(
            text = "La comida en México es una de las más ricas y variadas del mundo. " +
                    "Se caracteriza por su mezcla de sabores, colores y tradiciones que combinan " +
                    "ingredientes prehispánicos, como el maíz, el chile y el frijol, con influencias " +
                    "españolas y de otras culturas.",
            fontSize = 18.sp,
            lineHeight = 24.sp,
            color = Color.Black,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun PantallaJapon() {

    val imagenes = listOf(
        Pair(R.drawable.bent, "Bento"),
        Pair(R.drawable.ram, "Ramen"),
        Pair(R.drawable.sopmi, "Sopa de miso"),
        Pair(R.drawable.sush, "Sushi"),
        Pair(R.drawable.tako, "Takoyaki")
    )

    // Usamos un LazyColumn para poder hacer scroll
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        /** 🗾 Encabezado */
        item {
            Text(
                text = "Japón",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 50.sp,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "La comida en Japón se caracteriza por su " +
                        "equilibrio, frescura y presentación cuidada. " +
                        "Se basa en ingredientes como el arroz, el pescado, las verduras y la soya, " +
                        "buscando siempre resaltar el sabor natural de los alimentos.",
                fontSize = 18.sp,
                lineHeight = 24.sp,
                color = Color.Black,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        /** 🍱 Lista de cartas */
        items(imagenes) { (imagenRes, nombre) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { /* Aquí puedes agregar una acción si lo deseas */ },
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = imagenRes),
                        contentDescription = nombre,
                        modifier = Modifier
                            .size(250.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .border(2.dp, Color.Gray, RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = nombre,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Espacio final para scroll cómodo
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
fun PantallaItalia() {

    val imagenes = listOf(
        Pair(R.drawable.espa, "Spaghetti"),
        Pair(R.drawable.gela, "Gelato"),
        Pair(R.drawable.pizza, "Pizza"),
        Pair(R.drawable.tirami, "Tiramisú"),
        Pair(R.drawable.laza, "Lasagna")
    )

    // 🧭 Scroll automático con LazyVerticalGrid
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        /** 🇮🇹 Encabezado */
        Text(
            text = "Italia",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 50.sp,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "La comida en Italia es reconocida por su sencillez, frescura y sabor auténtico. " +
                    "Se basa en ingredientes naturales como el tomate, el aceite de oliva, la pasta, el queso y las hierbas aromáticas. " +
                    "Cada región tiene sus especialidades, desde la pizza napolitana hasta la pasta boloñesa o los raviolis del norte.",
            fontSize = 18.sp,
            lineHeight = 24.sp,
            color = Color.Black,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        /** 🍕 Grid de cartas (2 columnas) */
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            content = {
                items(imagenes) { (imagenRes, nombre) ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /* acción al tocar */ },
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Image(
                                painter = painterResource(id = imagenRes),
                                contentDescription = nombre,
                                modifier = Modifier
                                    .height(150.dp)
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(2.dp, Color.Gray, RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = nombre,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        )
    }
}