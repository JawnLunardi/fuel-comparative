package com.joaolunardi.gasOrEthanol

import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.KeyboardType
import com.joaolunardi.gasOrEthanol.ui.theme.GasOrEthanolTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GasOrEthanolTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App() {
    val image = painterResource(R.drawable.bomba_de_gasolina)

    var valorGasolina by remember {
        mutableStateOf("")
    }

    var valorEtanol by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier.padding(horizontal = 0.dp, vertical = 80.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }

    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.animateScrollTo(scrollState.maxValue, tween(300))
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Etanol x Gasolina", style = TextStyle(
                    color = Color.Red,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            AnimatedVisibility(visible = valorEtanol.isNotBlank() && valorGasolina.isNotBlank()) {
                if (valorEtanol.isNotBlank() && valorGasolina.isNotBlank()) {
                    val fuel = if (valorEtanol.toDouble() / valorGasolina.toDouble() > 0.7) {
                        "Gasolina"
                    } else {
                        "Etanol"
                    }
                    Text(
                        text = fuel, style = TextStyle(
                            color = Color.Black,
                            fontSize = 40.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                }
            }

            TextField(
                value = valorGasolina,
                onValueChange = {
                    valorGasolina = it
                },
                label = { Text(text = "Gasolina") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)


            )
            TextField(
                value = valorEtanol,
                onValueChange = {
                    valorEtanol = it
                },
                label = { Text(text = "Etanol") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GasOrEthanolTheme {
        App()
    }
}