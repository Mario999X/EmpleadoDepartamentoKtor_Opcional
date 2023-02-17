package resa.mario.models

import java.util.UUID

data class Usuario(
    val id: UUID = UUID.randomUUID(),
    val username: String,
    val password: String,
    val role: String = Role.USER.name
) {
    enum class Role {
        USER, ADMIN
    }

    override fun toString(): String {
        return "Usuario(id=$id, username='$username', password='$password', role='$role')"
    }

}
