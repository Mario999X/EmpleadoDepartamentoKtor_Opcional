package resa.mario.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import resa.mario.dto.DepartamentoDTO
import resa.mario.mappers.toDTO
import resa.mario.mappers.toDepartamento
import resa.mario.services.departamento.DepartamentoServiceImpl
import java.util.*

private const val END_POINT = "api/departamentos"

fun Application.departamentosRoutes() {

    val departamentoService: DepartamentoServiceImpl by inject()

    routing {
        route("/$END_POINT") {
            // Get All
            get {
                val result = mutableListOf<DepartamentoDTO>()

                // Procesamos el flujo
                departamentoService.findAll().collect {
                    result.add(it.toDTO())
                }

                // Para poder ver los id
                departamentoService.findAll().collect {
                    println(it)
                }

                call.respond(HttpStatusCode.OK, result)
            }

            // Get by Id /endpoint/id
            get("{id}") {
                try {
                    val id = call.parameters["id"]!!
                    val departamento = departamentoService.findById(UUID.fromString(id))

                    call.respond(HttpStatusCode.OK, departamento.toDTO())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            // Post /endpoint
            /*
             * EN THUNDERCLIENT, HEADERS -> Content-Type __ application/json
             * Luego en BODY -> JSON
            */
            post {
                try {
                    val departamentoReceive = call.receive<DepartamentoDTO>()
                    val departamentoSave = departamentoService.save(departamentoReceive.toDepartamento())
                    call.respond(
                        HttpStatusCode.Created, departamentoService.findById(departamentoSave.id).toDTO()
                    )
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            put("{id}") {
                try {
                    val id = call.parameters["id"]!!
                    val request = call.receive<DepartamentoDTO>()
                    val departamento = departamentoService.update(UUID.fromString(id), request.toDepartamento())
                    call.respond(HttpStatusCode.OK, departamento.toString())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            delete("{id}") {
                try {
                    val id = call.parameters["id"]!!

                    val departamentoDelete = departamentoService.findById(UUID.fromString(id))
                    departamentoService.delete(departamentoDelete)
                    call.respond(HttpStatusCode.NoContent)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.NotFound, e.message.toString())
                }
            }
        }
    }
}
