package com.glqdlt.session;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.session.config.annotation.web.http.SpringHttpSessionConfiguration;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * {@link org.springframework.session.jdbc.config.annotation.web.http.JdbcHttpSessionConfiguration}
 * 에서 dataSource 를 직접 핸들링 하고 싶었는 데 그게 어려워서 Adapter 클래스를 하나 만든게 이거임.
 * 프로퍼티 파일에서 session.jdbc.enable 의 키로 되는 값에 따라 기본 in memory 기반 session 을 탈 지, db 기반으로 탈 지 처리하게 해놓음.
 * 실제 쿼리나 JDBC 에 access 하는 주체는 {@link JdbcIndexedSessionRepository} 를 참고
 *
 * @author Jhun
 * @see org.springframework.session.jdbc.config.annotation.web.http.JdbcHttpSessionConfiguration
 * 2020-02-11
 */
public class JdbcSessionConfigurationAdapter extends SpringHttpSessionConfiguration {

    private DataSource dataSource;
    private PlatformTransactionManager transactionTemplate;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public PlatformTransactionManager getTransactionTemplate() {
        return transactionTemplate;
    }

    public void setTransactionTemplate(PlatformTransactionManager transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    private JdbcTemplate createJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    private TransactionTemplate createTransactionTemplate(PlatformTransactionManager transactionManager) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.afterPropertiesSet();
        return transactionTemplate;
    }

    public JdbcIndexedSessionRepository jdbcSessionRepository() {
        JdbcTemplate jdbcTemplate = createJdbcTemplate(getDataSource());
        TransactionTemplate transactionTemplate = createTransactionTemplate(getTransactionTemplate());
        return new JdbcIndexedSessionRepository(jdbcTemplate, transactionTemplate);
    }
}
