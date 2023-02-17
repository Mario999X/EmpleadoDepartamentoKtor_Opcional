package resa.mario.dto

import kotlinx.serialization.Serializable

@Serializable
data class DepartamentoDTO(
    val nombre: String,
    val presupuesto: Double
)