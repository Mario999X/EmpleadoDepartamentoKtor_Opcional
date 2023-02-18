package resa.mario.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import resa.mario.services.StorageService
import java.util.UUID

private const val END_POINT = "api/storage"

fun Application.storageRoutes() {

    val storageService: StorageService by inject()

    routing {
        route("/$END_POINT") {
            // get file
            get("{name}") {
                try {
                    val name = call.parameters["name"].toString()
                    val file = storageService.getFile(name)

                    call.respond(HttpStatusCode.OK, file)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.NotFound, e.message.toString())
                }
            }

            // Post file
            post {
                try {
                    val readChannel = call.receiveChannel()

                    // Damos nombre al archivo, y lo almacenamos con el metodo correspondiente
                    val fileName = UUID.randomUUID().toString()
                    val res = storageService.saveFile(fileName, readChannel)

                    call.respond(HttpStatusCode.OK, res)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }
        }
    }
}