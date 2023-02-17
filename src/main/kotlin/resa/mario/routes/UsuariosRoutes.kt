package resa.mario.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import resa.mario.dto.UsuarioDTOLogin
import resa.mario.dto.UsuarioDTORegister
import resa.mario.mappers.toDTO
import resa.mario.mappers.toUsuario
import resa.mario.services.usuario.UsuarioServiceImpl

private const val END_POINT = "api/usuarios"

fun Application.usuariosRoutes() {
    val usuarioService: UsuarioServiceImpl by inject()

    // TOKENS

    routing {
        route("/$END_POINT") {

            // 2. Generamos la ruta para el registro
            post("/register") {
                try {
                    val dto = call.receive<UsuarioDTORegister>()
                    val user = usuarioService.save(dto.toUsuario())
                    call.respond(HttpStatusCode.Created, user.toDTO())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            // 3. Generamos la ruta para el login
            post("/login") {
                try {
                    val dto = call.receive<UsuarioDTOLogin>()
                    val user = usuarioService.login(dto.username, dto.password)

                    user?.let {
                        //val token = tokensService.generateJWT(user)
                        call.respond(HttpStatusCode.OK, user.toDTO()) // token
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

        }
    }
}