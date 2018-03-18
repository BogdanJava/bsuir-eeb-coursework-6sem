package by.bsuir.eeb.rsoicoursework.config;

import by.bsuir.eeb.rsoicoursework.model.Address;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.hateoas.config.EnableEntityLinks;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Bogdan Shishkin
 * project: rsoi-coursework
 * date/time: 18.03.2018 / 1:25
 * email: bogdanshishkin1998@gmail.com
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "by.bsuir.eeb.rsoicoursework.dao")
public class DataConfig {

    private Environment env;

    @Autowired
    public void setEnv(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory =
                new LocalContainerEntityManagerFactoryBean();

        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setPackagesToScan("by.bsuir.eeb.rsoicoursework.model");
        entityManagerFactory.afterPropertiesSet();

        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        jpaProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));

        entityManagerFactory.setJpaProperties(jpaProperties);

        return entityManagerFactory;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }
}
