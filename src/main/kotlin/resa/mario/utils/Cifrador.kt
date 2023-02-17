package resa.mario.utils

import com.toxicbakery.bcrypt.Bcrypt

object Cifrador {

    private const val BRCYPT_SALT = 12

    fun cipher(password: String): String {
        return Bcrypt.hash(password, BRCYPT_SALT).decodeToString()
    }

}