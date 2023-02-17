package resa.mario.mappers

import resa.mario.dto.EmpleadoDTO
import resa.mario.models.Empleado
import java.util.UUID

fun Empleado.toDTO(): EmpleadoDTO {
    return EmpleadoDTO(
        nombre, email, departamentoId.toString(), avatar
    )
}

fun EmpleadoDTO.toEmpleado(): Empleado {
    return Empleado(
        nombre = nombre,
        email = email,
        departamentoId = UUID.fromString(departamentoId),
        avatar = avatar
    )
}