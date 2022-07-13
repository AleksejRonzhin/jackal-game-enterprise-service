package ru.rsreu.jackal.security.config

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import ru.rsreu.jackal.security.jwt.JwtConfigurer

@EnableWebSecurity
class SecurityConfiguration(
    private val jwtConfigurer: JwtConfigurer,
    private val authenticationEntryPoint: AuthenticationEntryPoint
) {
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
            .and()
            .authorizeRequests {
                it
                    .antMatchers("/auth/**", "/refresh/**").permitAll()
            }
            .apply(jwtConfigurer)
            .and()
            .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
        return http.build()
    }

    @Bean
    fun corsConfigurer(): WebMvcConfigurer = object : WebMvcConfigurer {
        override fun addCorsMappings(registry: CorsRegistry) {
            registry.addMapping("/**").allowedOrigins("*").allowedMethods("*")
        }
    }
}