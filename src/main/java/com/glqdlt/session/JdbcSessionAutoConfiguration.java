package com.glqdlt.session;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author Jhun
 * 2020-02-11
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(JdbcSessionAdapterProperties.class)
public class JdbcSessionAutoConfiguration {

    private JdbcSessionConfigurationAdapter jdbcSessionConfigurationAdapter;
    private JdbcSessionAdapterProperties jdbcSessionAdapterProperties;

    public void setJdbcSessionAdapterProperties(JdbcSessionAdapterProperties jdbcSessionAdapterProperties) {
        this.jdbcSessionAdapterProperties = jdbcSessionAdapterProperties;
    }

    public JdbcSessionAdapterProperties getProps() {
        return jdbcSessionAdapterProperties;
    }

    public JdbcSessionAutoConfiguration() {
        this.jdbcSessionConfigurationAdapter = new JdbcSessionConfigurationAdapter();
        final DataSource ds = makeDataSource();
        this.jdbcSessionConfigurationAdapter.setDataSource(ds);
        this.jdbcSessionConfigurationAdapter.setTransactionTemplate(makeTransactionTemplate(ds));
    }

    private PlatformTransactionManager makeTransactionTemplate(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);

    }

    private DataSource makeDataSource() {
        return DataSourceBuilder.create()
                .url(getProps().getUrl())
                .password(getProps().getPassword())
                .driverClassName(getProps().getDriverClassName())
                .username(getProps().getUser())
                .build();
    }

    @Bean
    public JdbcIndexedSessionRepository sessionRepository() {
        return this.jdbcSessionConfigurationAdapter.jdbcSessionRepository();
    }

}
