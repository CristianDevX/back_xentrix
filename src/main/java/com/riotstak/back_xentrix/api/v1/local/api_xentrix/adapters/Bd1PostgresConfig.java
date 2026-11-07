
package com.riotstak.back_xentrix.api.v1.local.api_xentrix.adapters;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.riotstak.back_xentrix.api.v1.local.api_xentrix.aplications_logs.adapters.bd1.AplicationLog;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {
                "com.riotstak.back_xentrix.api.v1.local.api_xentrix.events.adapters.bd1",
                "com.riotstak.back_xentrix.api.v1.local.api_xentrix.categories.adapters.bd1",
                "com.riotstak.back_xentrix.api.v1.local.api_xentrix.venues.adapters.bd1",
                "com.riotstak.back_xentrix.api.v1.local.api_xentrix.aplications_logs.adapters.bd1"
}, entityManagerFactoryRef = "postgresEntityManagerFactory", transactionManagerRef = "postgresTransactionManager")
public class Bd1PostgresConfig {

        private final Environment env;

        public Bd1PostgresConfig(Environment env) {
                this.env = env;
        }

        @Bean(name = "postgresDataSource")
        public DataSource postgresDataSource() {

                DriverManagerDataSource dataSource = new DriverManagerDataSource();

                dataSource.setUrl(env.getProperty("bd1postgres.datasource.url"));
                dataSource.setUsername(env.getProperty("bd1postgres.datasource.username"));
                dataSource.setPassword(env.getProperty("bd1postgres.datasource.password"));
                dataSource.setDriverClassName(
                                env.getProperty("bd1postgres.datasource.driver-class-name"));

                return dataSource;
        }

        @Primary
        @Bean(name = "postgresEntityManagerFactory")
        public LocalContainerEntityManagerFactoryBean entityManagerFactory(
                        @Qualifier("postgresDataSource") DataSource dataSource) {

                LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

                em.setDataSource(dataSource);

                // Paquete exacto donde se encuentra AplicationLog
                em.setPackagesToScan(
                                "com.riotstak.back_xentrix.api.v1.local.api_xentrix.events.adapters.bd1",
                                "com.riotstak.back_xentrix.api.v1.local.api_xentrix.categories.adapters.bd1",
                                "com.riotstak.back_xentrix.api.v1.local.api_xentrix.venues.adapters.bd1",
                                "com.riotstak.back_xentrix.api.v1.local.api_xentrix.aplications_logs.adapters.bd1");

                em.setPersistenceUnitName("Bd1PostgresConfig");

                HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

                em.setJpaVendorAdapter(vendorAdapter);

                Map<String, Object> properties = new HashMap<>();

                properties.put(
                                "hibernate.hbm2ddl.auto",
                                env.getProperty("bd1postgres.jpa.hibernate.ddl-auto"));

                properties.put(
                                "hibernate.show-sql",
                                env.getProperty("bd1postgres.jpa.show-sql"));

                properties.put(
                                "hibernate.dialect",
                                env.getProperty("bd1postgres.jpa.properties.hibernate.dialect"));

                em.setJpaPropertyMap(properties);

                return em;
        }

        @Primary
        @Bean(name = "postgresTransactionManager")
        public PlatformTransactionManager transactionManager(
                        @Qualifier("postgresEntityManagerFactory") EntityManagerFactory entityManagerFactory) {

                return new JpaTransactionManager(entityManagerFactory);
        }

        @Bean
        public CommandLineRunner verificarEntidades(
                        @Qualifier("postgresEntityManagerFactory") EntityManagerFactory entityManagerFactory) {

                return args -> {

                        boolean existe = entityManagerFactory
                                        .getMetamodel()
                                        .getEntities()
                                        .stream()
                                        .anyMatch(entity -> entity.getJavaType().equals(AplicationLog.class));

                        System.out.println("--------------------------------");
                        System.out.println("VERIFICACIÓN ENTIDAD BD1");
                        System.out.println("AplicationLog registrada: " + existe);
                        System.out.println("--------------------------------");
                };
        }
}
