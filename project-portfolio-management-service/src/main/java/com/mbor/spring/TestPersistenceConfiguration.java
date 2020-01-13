package com.mbor.spring;

import com.mbor.domain.*;
import com.mbor.domain.employeeinproject.*;
import com.mbor.domain.security.Privilege;
import com.mbor.domain.security.Role;
import com.mbor.domain.security.User;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.H2Dialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@Profile("!prod")
public class TestPersistenceConfiguration {

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
        localSessionFactoryBean.setAnnotatedClasses(RealEndDate.class, Project.class, BusinessUnit.class, Employee.class, BusinessRelationManager.class, BusinessEmployee.class,  Consultant.class, Director.class, Supervisor.class, ProjectRole.class, BusinessLeader.class, ProjectManager.class, ResourceManager.class, SolutionArchitect.class, User.class, Role.class, Privilege.class);
        return localSessionFactoryBean;
    }

    private Properties hibernateProperties(){
        Properties properties = new Properties();
        properties.setProperty(AvailableSettings.DIALECT, H2Dialect.class.getName());
        properties.setProperty(AvailableSettings.SHOW_SQL, String.valueOf(true));
        properties.setProperty(AvailableSettings.HBM2DDL_AUTO, "create-drop");
        return properties;

    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(){
        final HibernateTransactionManager platformTransactionManager = new HibernateTransactionManager();
        platformTransactionManager.setSessionFactory(localSessionFactoryBean().getObject());
        return platformTransactionManager;
    }

}
