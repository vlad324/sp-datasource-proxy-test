package io.github.vlad324.storedprocedure.config;

import io.github.vlad324.storedprocedure.service.impl.sp.GetUserSP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author v.radkevich
 * @since 2/5/18
 */
@Configuration
@Slf4j
public class DatasourceConfig {

    @Bean
    @Primary
    public DataSource dataSource(final DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean
    public GetUserSP getUserSP(final DataSource dataSource) {
        return new GetUserSP(dataSource, "get_user_by_name");
    }
}
