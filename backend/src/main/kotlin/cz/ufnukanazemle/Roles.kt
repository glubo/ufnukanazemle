package cz.ufnukanazemle


class Roles {
    enum class RolesEnum(
        val jwtName: String
    ) {
        ROLE_USER(Roles.ROLE_USER),
        ROLE_ADMIN(Roles.ROLE_ADMIN)
    }
    companion object {
        const val ROLE_USER = "user"
        const val ROLE_ADMIN = "admin"
    }
}