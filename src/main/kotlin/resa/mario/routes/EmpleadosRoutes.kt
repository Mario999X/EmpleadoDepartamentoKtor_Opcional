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
import resa.mario.services.StorageService
import resa.mario.services.empleado.EmpleadoServiceImpl
import java.util.*

private const val END_POINT = "api/empleados"

fun Application.empleadosRoutes() {

    val empleadoService: EmpleadoServiceImpl by inject()

    val storageService: StorageService by inject()

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
                    // Recibimos al empleado
                    val empleadoReceive = call.receive<EmpleadoDTO>()

                    // DTO -> MODEL
                    val empleadoSave = empleadoReceive.toEmpleado()

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

            // Actualizar solo el avatar
            put("/avatar/{id}") {
                try {
                    // Vaya movida lo de los ficheros
                    // Recibimos tanto el avatar como el id
                    val avatar = call.receiveChannel()
                    val id = call.parameters["id"]!!

                    // Obtenemos al empleado
                    val empleadoUpdate = empleadoService.findById(UUID.fromString(id))

                    // Damos nombre al archivo y lo almacenamos
                    val fileName = "${empleadoUpdate.id}.png"
                    storageService.saveFile(fileName, avatar)

                    // Actualizamos el avatar al usuario
                    empleadoUpdate.avatar = fileName
                    empleadoService.update(empleadoUpdate.id, empleadoUpdate)

                    call.respond(HttpStatusCode.OK, empleadoUpdate.toDTO())
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