package resa.mario.mappers

import resa.mario.dto.EmpleadoDTO
import resa.mario.models.Empleado

fun Empleado.toDTO(): EmpleadoDTO {
    return EmpleadoDTO(
        nombre, email, avatar
    )
}

fun EmpleadoDTO.toEmpleado(): Empleado {
    return Empleado(
        nombre = nombre,
        email = email,
        avatar = avatar
    )
}