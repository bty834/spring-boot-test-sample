package io.github.bty834.infrastructure.repository.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Profile("test")
@Configuration
public class H2DataSourceConfig {

    @Bean
    public DataSource h2Datasource() {
        return new EmbeddedDatabaseBuilder()
                   .setType(EmbeddedDatabaseType.H2)
                   .setName("test")
                   .setScriptEncoding("UTF-8")
                   .addScripts("h2/init-db-mode.sql")
                   .build();
    }
}
