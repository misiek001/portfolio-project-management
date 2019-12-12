package com.misiek.spring;

import com.misiek.domain.*;
import com.misiek.domain.employeeinproject.*;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.H2Dialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class TestPersistenceConfiguration {

    @Bean
    public PlatformTransactionManager platformTransactionManager(){
        final HibernateTransactionManager platformTransactionManager = new HibernateTransactionManager();
        platformTransactionManager.setSessionFactory(localSessionFactoryBean().getObject());
        return platformTransactionManager;
    }

    @Bean
    public DataSource dataSource(){
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    @Bean
    public LocalSessionFactoryBean localSessionFactoryBean(){
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource());
        localSessionFactoryBean.setHibernateProperties(hibernateProperties());
        localSessionFactoryBean.setAnnotatedClasses(Project.class, BusinessUnit.class, Employee.class, BusinessRelationManager.class,  Consultant.class, Director.class, Supervisor.class, ProjectRole.class, BusinessLeader.class, ProjectManager.class, ResourceManager.class, SolutionArchitect.class);
        return localSessionFactoryBean;
    }

    private Properties hibernateProperties(){
        Properties properties = new Properties();
        properties.setProperty(AvailableSettings.DIALECT, H2Dialect.class.getName());
        properties.setProperty(AvailableSettings.SHOW_SQL, String.valueOf(true));
        properties.setProperty(AvailableSettings.HBM2DDL_AUTO, "create-drop");
        return properties;

    }

}
