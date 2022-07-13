package ru.rsreu.jackal.configuration

import org.flywaydb.core.Flyway
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@Configuration
@ComponentScan
class MigrationConfig(dataSource: DataSource) {
    init {
        Flyway.configure().baselineOnMigrate(true).dataSource(dataSource).load().migrate()
    }
}