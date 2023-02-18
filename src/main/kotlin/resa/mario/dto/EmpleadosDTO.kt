package resa.mario.dto

import kotlinx.serialization.Serializable

@Serializable
data class EmpleadoDTO(
    val nombre: String,
    val email: String,
    val departamentoId: String? = null,
    val avatar: String?
)