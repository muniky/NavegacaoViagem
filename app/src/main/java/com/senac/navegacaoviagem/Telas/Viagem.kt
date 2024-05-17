import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.senac.navegacaoviagem.Componentes.DatePickerComponent
import com.senac.navegacaoviagem.Componentes.DestinationInput
import com.senac.navegacaoviagem.Componentes.TripTypeInput
import com.senac.navegacaoviagem.Componentes.ValueInput
import com.senac.navegacaoviagem.Model.TipoViagem
import com.senac.navegacaoviagem.Model.Viagem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

@Composable

fun Viagem(navController: NavController){
    val snackbarHostState = remember { SnackbarHostState() }
    val viagem = remember {
        mutableStateOf(
            Viagem(
                destino = "",
                tipo = TipoViagem.LAZER,
                DataInicial = Date(),
                DataFinal = Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000),
                Valor = 0.0
            )
        )
    }
    Scaffold (
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)},
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Gray.copy(alpha = 0.1f))
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(onClick = {navController.navigate("login")}) {
                            Text("<--")
                        }
                        Text(
                            text = "Nova Viagem",
                            modifier = Modifier.weight(0.1f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    DestinationInput(
                        destination = viagem.value.destino,
                        onDestinationChanged = { destination -> viagem.value = viagem.value.copy(destino = destination) }
                    )
                    TripTypeInput(
                        tripType = viagem.value.tipo,
                        onTripTypeChanged = { type -> viagem.value = viagem.value.copy(tipo = type) }
                    )
                    ValueInput(
                        value = formatValue(viagem.value.Valor),
                        onValueChanged = { newValue ->
                            viagem.value = viagem.value.copy(Valor = newValue.toDoubleOrNull() ?: 0.0)
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    DatePickerComponent(
                        label = "Data Início",
                        date = viagem.value.DataInicial,
                        onDateSelected = { newDate ->
                            viagem.value = viagem.value.copy(DataInicial = newDate)
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    DatePickerComponent(
                        label = "Data Final",
                        date = viagem.value.DataFinal,
                        onDateSelected = { newDate ->
                            viagem.value = viagem.value.copy(DataFinal = newDate)
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            CoroutineScope(Dispatchers.Main).launch {
                                snackbarHostState.showSnackbar("Viagem Registrada!")
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Salvar Viagem")
                    }
                }
            }
        }
    )
}

@Composable
fun formatValue(value: Double): String {
    val intValue = value.toInt()
    return if (value == intValue.toDouble()) {
        intValue.toString()
    } else {
        value.toString()
    }
}