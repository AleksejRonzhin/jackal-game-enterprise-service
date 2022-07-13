package ru.rsreu.jackal.security.config

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
class SecurityConfiguration {
    @Bean
    fun webSecurityCustomizer() = WebSecurityCustomizer {
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .httpBasic().disable()
            .cors().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        return http.build()
    }
}