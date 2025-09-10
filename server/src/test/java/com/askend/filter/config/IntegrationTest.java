package com.askend.filter.config;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class IntegrationTest {

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected JdbcTemplate jdbcTemplate;

  @AfterEach
  void cleanDatabase() {
    JdbcTestUtils.deleteFromTables(this.jdbcTemplate, this.getTableNames());
  }

  protected abstract String[] getTableNames();
}
