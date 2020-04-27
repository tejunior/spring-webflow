package com.teo.springwebflow.configuration

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.test.context.support.TestPropertySourceUtils
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy
import java.time.Duration
import java.time.temporal.ChronoUnit

class PostgresTestContextInitializer : ApplicationContextInitializer<GenericApplicationContext> {

    override fun initialize(applicationContext: GenericApplicationContext) {
        val dbUrl = postgresSQLContainer.dbUrl()
        val dbUsername = postgresSQLContainer.username
        val dbPassword = postgresSQLContainer.password

        addInlineDbProperties(applicationContext, dbUrl, dbUsername, dbPassword)
    }

    private fun addInlineDbProperties(
        applicationContext: GenericApplicationContext,
        url: String,
        username: String,
        password: String
    ) {
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
            applicationContext,
            "spring.datasource.url=$url",
            "spring.datasource.username=$username",
            "spring.datasource.password=$password"
        )
    }

    class KPostgresContainer(imageName: String) : PostgreSQLContainer<KPostgresContainer>(imageName) {
        @Suppress("MagicNumber")
        fun dbUrl(): String = "jdbc:postgresql://$containerIpAddress:${getMappedPort(5432)}/mydb"
    }

    companion object {
        val postgresSQLContainer: KPostgresContainer = KPostgresContainer("postgres")
            .withDatabaseName("mydb")
            .withUsername("user")
            .withPassword("pass")
            .withExposedPorts(5432).waitingFor(
                LogMessageWaitStrategy()
                    .withRegEx(".*database system is ready to accept connections.*\\s")
                    .withTimes(2)
                    .withStartupTimeout(Duration.of(60, ChronoUnit.SECONDS))
            )

        init {
            if (!postgresSQLContainer.isRunning) {
                postgresSQLContainer.start()
            }
        }
    }
}
