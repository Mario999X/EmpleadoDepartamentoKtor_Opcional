package resa.mario.models

import java.util.UUID

data class Empleado(
    val id: UUID = UUID.randomUUID(),
    val nombre: String,
    val email: String,
    val departamentoId: UUID? = null,
    val avatar: String
) {
    override fun toString(): String {
        return "Empleado(id=$id, nombre='$nombre', email='$email', departamentoId=$departamentoId, avatar='$avatar')"
    }
}
