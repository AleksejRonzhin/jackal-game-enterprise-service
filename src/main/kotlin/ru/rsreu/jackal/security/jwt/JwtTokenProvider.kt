package ru.rsreu.jackal.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
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
            .setClaims(formAccessClaims(user))
            .setIssuedAt(now)
            .setExpiration(Date(now.time + tokenExpiringMilliSeconds))
            .signWith(secretKey)
            .setHeaderParam("typ", "JWT")
            .compact()
    }

    private fun formAccessClaims(user: User): Claims {
        val claims = Jwts.claims().setSubject(user.id!!.toString())
        claims["username"] = user.name
        claims["picture_url"] = user.pictureUrl
        if (user.permissions.isNotEmpty()) {
            claims["permissions"] = user.permissions.joinToString { it.toString() }
        }
        return claims
    }

    fun createRefreshToken(accessToken: String, username: String): String {
        val claims = Jwts.claims().setSubject(accessToken)
        claims["username"] = username
        return Jwts.builder()
            .setClaims(claims)
            .signWith(secretKey)
            .setHeaderParam("typ", "JWT")
            .compact()
    }

    fun getJwsClaims(jwt: String): Jws<Claims> = jwtParser.parseClaimsJws(jwt)

    fun getJwtFromRefreshClaims(claims: Jws<Claims>): String = claims.body.subject

    fun getUsernameFromRefreshClaims(claims: Jws<Claims>): String = claims.body["username"].toString()
}