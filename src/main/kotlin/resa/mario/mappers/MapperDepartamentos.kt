package resa.mario.mappers

import resa.mario.dto.DepartamentoDTO
import resa.mario.models.Departamento

fun Departamento.toDTO(): DepartamentoDTO {
    return DepartamentoDTO(
        nombre, presupuesto
    )
}

fun DepartamentoDTO.toDepartamento(): Departamento {
    return Departamento(
        nombre = nombre,
        presupuesto = presupuesto
    )
}