package com.gh.mirocodij.inventory.system.springinventorysystem.fixtures;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = {PgContainer.class})
public class BaseTest {
}
