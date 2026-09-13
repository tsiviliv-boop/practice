package ci.nsu.mobile.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

class ColorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColorScreen()
        }
    }
}

// Структура данных: имя цвета -> Color
val colorPalette: Map<String, Color> = mapOf(
    "Red" to Color.Red,
    "Orange" to Color(0xFFFFA500),
    "Yellow" to Color.Yellow,
    "Green" to Color.Green,
    "Blue" to Color.Blue,
    "Indigo" to Color(0xFF4B0082),
    "Violet" to Color(0xFF8A2BE2)
)

@Composable
fun ColorScreen() {
    var inputText by remember { mutableStateOf("") }
    var buttonColor by remember { mutableStateOf(Color.Gray) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Название цвета") },
            singleLine = true,  // Запрещает переход на новую строку
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done  // Меняет клавишу Enter на "Готово"
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Здесь код поиска цвета (тот же, что и в кнопке)
                    val found = colorPalette[inputText]
                    if (found != null) {
                        buttonColor = found
                    } else {
                        Log.d("ColorSearch", "Цвет \"$inputText\" не найден")
                    }
                }
            )
        )

        Button(
            onClick = {
                val found = colorPalette[inputText]
                if (found != null) {
                    buttonColor = found
                } else {
                    Log.d("ColorSearch", "Цвет \"$inputText\" не найден")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Применить цвет")
        }

        // Палитра
        colorPalette.forEach { (name, color) ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(color, RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Text(name)
            }
        }
    }
}