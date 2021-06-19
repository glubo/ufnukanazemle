package cz.ufnukanazemle


class Roles {
    enum class RolesEnum(
        val jwtName: String
    ) {
        ROLE_USER(Roles.ROLE_USER)
    }
    companion object {
        const val ROLE_USER = "user"
    }
}