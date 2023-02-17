package resa.mario.dto

import kotlinx.serialization.Serializable

@Serializable
data class EmpleadoDTO(
    val nombre: String,
    val email: String,
    val avatar: String
)