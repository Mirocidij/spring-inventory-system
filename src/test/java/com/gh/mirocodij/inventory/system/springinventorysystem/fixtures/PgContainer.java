package com.gh.mirocodij.inventory.system.springinventorysystem.fixtures;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

@Slf4j
public class PgContainer extends PostgreSQLContainer<PgContainer>
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final String IMAGE_VERSION = "postgres:12.4";

    private static final PgContainer container;

    static {
        container = new PgContainer()
                .withDatabaseName("inventory_system")
                .withUsername("inventory_manager")
                .withPassword("12345");
    }

    public PgContainer() {
        super(IMAGE_VERSION);
    }

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
        container.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());

        log.info("test db url: {}", container.getJdbcUrl());
    }
}
