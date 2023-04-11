package com.example.domain.myapp.general.dataaccess.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.devonfw.module.basic.common.api.config.SpringProfileConstants;
import com.devonfw.module.jpa.dataaccess.api.JpaInitializer;

/**
 * Spring {@link Configuration} for JPA.
 */
@Configuration
@Profile(SpringProfileConstants.NOT_JUNIT)
public class JpaConfig {

  /**
   * @return the {@link JpaInitializer} to register the {@link javax.persistence.EntityManager} and make
   *         {@link com.devonfw.module.jpa.dataaccess.api.JpaHelper} functional.
   */
  @Bean
  public JpaInitializer jpaInitializer() {

    return new JpaInitializer();
  }

}
