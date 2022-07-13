package ru.rsreu.jackal

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [UserDetailsServiceAutoConfiguration::class])
class JackalGameEnterpriseServiceApplication

fun main(args: Array<String>) {
    runApplication<JackalGameEnterpriseServiceApplication>(*args)
}
