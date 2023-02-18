package resa.mario.db

import resa.mario.dto.UsuarioDTORegister
import resa.mario.models.Departamento
import resa.mario.models.Empleado
import resa.mario.models.Usuario
import java.util.*

fun getDepartamentos() = listOf(
    Departamento(
        id = UUID.fromString("1d89e51a-af79-11ed-afa1-0242ac120002"),
        nombre = "Interfaces",
        presupuesto = 500.0
    ),
    Departamento(
        id = UUID.fromString("2fa8d67a-af79-11ed-afa1-0242ac120002"),
        nombre = "Administracion",
        presupuesto = 700.0
    ),
    Departamento(
        id = UUID.fromString("36a508e0-af79-11ed-afa1-0242ac120002"),
        nombre = "PSP",
        presupuesto = 400.0
    )
)

fun getEmpleados() = listOf(
    Empleado(
        nombre = "Mario",
        email = "mario@gmail.com",
        departamentoId = getDepartamentos()[0].id,
        avatar = "Ejemplo"
    ),
    Empleado(
        nombre = "Alysys",
        email = "alysys@gmail.com",
        departamentoId = getDepartamentos()[1].id,
        avatar = "Ejemplo"
    ),
    Empleado(
        nombre = "Vincent",
        email = "vincent@gmail.com",
        departamentoId = getDepartamentos()[1].id,
        avatar = "Ejemplo"
    ),
    Empleado(nombre = "Kratos", email = "kratos@gmail.com", avatar = "Ejemplo"),
)

fun getUsuarios() = listOf(
    UsuarioDTORegister("Mario111", "1234", Usuario.Role.ADMIN.name),
    UsuarioDTORegister("Alysys222", "1234", Usuario.Role.USER.name)
)