package ru.rsreu.jackal.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import ru.rsreu.jackal.api.models.Permission
import ru.rsreu.jackal.api.models.User
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(
    @Value("\${security.jwt.secret}")
    private val secretKeyStringRepresentation: String,

    @Value("\${security.jwt.expiration-ms}")
    private val tokenExpiringMilliSeconds: Long
) {
    private val secretKey: SecretKey =
        Keys.hmacShaKeyFor(secretKeyStringRepresentation.toByteArray(StandardCharsets.UTF_8))

    private val jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build()

    fun createAccessToken(user: User): String {
        val now = Date()
        return Jwts.builder()
            .setClaims(
                formClaims(user.id!!, user.name, user.permissions)
            )
            .setIssuedAt(now)
            .setExpiration(Date(now.time + tokenExpiringMilliSeconds))
            .signWith(secretKey)
            .setHeaderParam("typ", "JWT")
            .compact()
    }

    private fun formClaims(id: Long, username: String, permissions: List<Permission>): Claims {
        val claims = Jwts.claims().setSubject(id.toString())
        claims["username"] = username
        if (permissions.isNotEmpty()) {
            claims["permissions"] = permissions.joinToString { it.toString() }
        }
        return claims
    }

    fun createRefreshToken(accessToken: String): String =
        Jwts.builder()
            .setClaims(
                Jwts.claims().setSubject(accessToken)
            )
            .signWith(secretKey)
            .setHeaderParam("typ", "JWT")
            .compact()

    fun getJwsClaims(jwt: String): Jws<Claims> = jwtParser.parseClaimsJws(jwt)

    fun getJwtFromRefresh(refreshJwt: String): String = getJwsClaims(refreshJwt).body.subject
}