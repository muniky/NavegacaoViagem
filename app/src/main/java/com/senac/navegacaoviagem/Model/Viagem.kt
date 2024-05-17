package com.senac.navegacaoviagem.Model

import java.util.Date

data class Viagem(
    var destino: String,
    var tipo: TipoViagem,
    var DataInicial: Date,
    var DataFinal: Date,
    var Valor: Double
)
enum class TipoViagem {
    LAZER, NEGOCIOS
}