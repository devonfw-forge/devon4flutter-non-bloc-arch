package com.example.domain.myapp.general.service.impl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

/**
 * This configuration class provides factory methods for several Spring security related beans.
 */
@Configuration
public class WebSecurityBeansConfig {

  /**
   * This method provides a new instance of {@code CsrfTokenRepository}
   *
   * @return the newly created {@code CsrfTokenRepository}
   */
  @Bean
  public CsrfTokenRepository csrfTokenRepository() {

    return new HttpSessionCsrfTokenRepository();
  }

  /**
   * This method provide a new instance of {@code DelegatingPasswordEncoder}
   *
   * @return the newly create {@code DelegatingPasswordEncoder}
   */
  @Bean
  public PasswordEncoder passwordEncoder() {

    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}
