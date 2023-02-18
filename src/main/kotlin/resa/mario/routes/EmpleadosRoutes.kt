package resa.mario.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import resa.mario.dto.EmpleadoDTO
import resa.mario.mappers.toDTO
import resa.mario.mappers.toEmpleado
import resa.mario.services.empleado.EmpleadoServiceImpl
import java.util.*

private const val END_POINT = "api/empleados"

fun Application.empleadosRoutes() {

    val empleadoService: EmpleadoServiceImpl by inject()

    routing {
        route("/$END_POINT") {
            // Get All
            get {
                val result = mutableListOf<EmpleadoDTO>()

                // Procesamos el flujo
                empleadoService.findAll().collect {
                    result.add(it.toDTO())
                }

                // Para poder ver los id
                empleadoService.findAll().collect {
                    println(it)
                }

                call.respond(HttpStatusCode.OK, result)
            }

            // Get By Id
            get("{id}") {
                try {
                    val id = call.parameters["id"]!!
                    val empleado = empleadoService.findById(UUID.fromString(id))

                    call.respond(HttpStatusCode.OK, empleado.toString())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            // Post /endpoint
            post {
                try {
                    val empleadoReceive = call.receive<EmpleadoDTO>()
                    val empleadoSave = empleadoService.save(empleadoReceive.toEmpleado())
                    call.respond(HttpStatusCode.Created, empleadoService.findById(empleadoSave.id).toDTO())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            put("{id}") {
                try {
                    val id = call.parameters["id"]!!
                    val request = call.receive<EmpleadoDTO>()
                    val empleado = empleadoService.update(UUID.fromString(id), request.toEmpleado())
                    call.respond(HttpStatusCode.OK, empleado.toDTO())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            delete("{id}") {
                try {
                    val id = call.parameters["id"]!!
                    val empleadoDelete = empleadoService.findById(UUID.fromString(id))

                    empleadoService.delete(empleadoDelete)
                    call.respond(HttpStatusCode.NoContent)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.NotFound, e.message.toString())
                }
            }
        }
    }
}