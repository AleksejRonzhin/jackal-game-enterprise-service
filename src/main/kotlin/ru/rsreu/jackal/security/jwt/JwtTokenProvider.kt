package ru.rsreu.jackal.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.stereotype.Component
import ru.rsreu.jackal.api.user.User
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
    private val principalJwtKey = "username"
    private val authorityJwtKey = "permissions"
    private val pictureJwtKey = "picture_url"

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
        claims[principalJwtKey] = user.name
        claims[pictureJwtKey] = user.pictureUrl
        if (user.permissions.isNotEmpty()) {
            claims[authorityJwtKey] = user.permissions.joinToString { it.toString() }
        }
        return claims
    }

    fun createRefreshToken(accessToken: String, username: String): String {
        val claims = Jwts.claims().setSubject(accessToken)
        claims[principalJwtKey] = username
        return Jwts.builder()
            .setClaims(claims)
            .signWith(secretKey)
            .setHeaderParam("typ", "JWT")
            .compact()
    }

    fun getJwsClaims(jwt: String): Jws<Claims> = jwtParser.parseClaimsJws(jwt)

    fun getJwtFromRefreshClaims(claims: Jws<Claims>): String = claims.body.subject

    fun getUsernameFromRefreshClaims(claims: Jws<Claims>): String = claims.body[principalJwtKey].toString()

    fun getAuthenticationFromJwt(jwt: String): Authentication {
        val claims = getJwsClaims(jwt).body
        return PreAuthenticatedAuthenticationToken(
            claims.subject,
            jwt,
            formAuthoritiesFromClaim(claims[authorityJwtKey])
        )
    }

    fun formAuthoritiesFromClaim(authoritiesClaim: Any?): MutableList<GrantedAuthority> =
        AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesClaim?.toString())
            ?: AuthorityUtils.NO_AUTHORITIES
}