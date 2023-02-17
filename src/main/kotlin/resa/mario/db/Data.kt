package resa.mario.db

import resa.mario.dto.UsuarioDTORegister
import resa.mario.models.Departamento
import resa.mario.models.Empleado
import resa.mario.models.Usuario

fun getDepartamentos() = listOf(
    Departamento(
        nombre = "Interfaces",
        presupuesto = 500.0
    ),
    Departamento(
        nombre = "Administracion",
        presupuesto = 700.0
    ),
    Departamento(
        nombre = "PSP",
        presupuesto = 400.0
    )
)

fun getEmpleados() = listOf(
    Empleado(nombre = "Mario", email = "mario@gmail.com", departamentoId = getDepartamentos()[0].id, avatar = "Ejemplo" ),
    Empleado(nombre = "Alysys", email = "alysys@gmail.com", departamentoId = getDepartamentos()[1].id, avatar = "Ejemplo"),
    Empleado(nombre = "Vincent", email = "vincent@gmail.com", departamentoId =  getDepartamentos()[1].id, avatar = "Ejemplo"),
    Empleado(nombre = "Kratos", email = "kratos@gmail.com", avatar = "Ejemplo"),
)

fun getUsuarios() = listOf(
    UsuarioDTORegister("Mario111", "1234", Usuario.Role.ADMIN.name),
    UsuarioDTORegister("Alysys222", "1234", Usuario.Role.USER.name)
)