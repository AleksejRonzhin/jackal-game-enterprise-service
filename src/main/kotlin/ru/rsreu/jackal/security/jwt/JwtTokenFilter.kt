package ru.rsreu.jackal.security.jwt

import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenFilter(private val jwtTokenProvider: JwtTokenProvider) : GenericFilterBean() {
    private val headerPrefix = "Bearer "
    private val startTokenIndex = 7

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        setAuthentication(resolveToken(request as HttpServletRequest))
        chain!!.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest?): String? {
        val bearerToken = request?.getHeader(AUTHORIZATION)
        if (StringUtils.hasText(bearerToken) && bearerToken!!.startsWith(headerPrefix)) {
            return bearerToken.substring(startTokenIndex)
        }
        return null
    }

    private fun setAuthentication(jwtToken: String?) {
        if (jwtToken != null) {
            val authentication = jwtTokenProvider.runCatching { getAuthenticationFromJwt(jwtToken) }.getOrNull()
            if (authentication != null) {
                SecurityContextHolder.getContext().authentication = authentication
            }
        }
    }
}