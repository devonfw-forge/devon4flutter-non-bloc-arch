package com.example.domain.myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import com.devonfw.module.jpa.dataaccess.api.AdvancedRevisionEntity;
import com.devonfw.module.jpa.dataaccess.impl.data.GenericRepositoryFactoryBean;

/**
 * Main entry point of this {@link SpringBootApplication}. Simply run this class to start this app.
 */
@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = GenericRepositoryFactoryBean.class)
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class SpringBootApp {

  /**
   * Entry point for spring-boot based app
   *
   * @param args - arguments
   */
  public static void main(String[] args) {

    SpringApplication.run(SpringBootApp.class, args);
  }
}
