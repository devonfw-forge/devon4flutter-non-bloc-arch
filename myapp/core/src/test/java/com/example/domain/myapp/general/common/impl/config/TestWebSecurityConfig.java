package com.example.domain.myapp.general.common.impl.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.domain.myapp.general.service.impl.config.BaseWebSecurityConfig;
import com.devonfw.module.basic.common.api.config.SpringProfileConstants;

/**
 * This type provides web security configuration for testing purposes.
 */
@Configuration
@EnableWebSecurity
@Profile(SpringProfileConstants.JUNIT)
public class TestWebSecurityConfig extends BaseWebSecurityConfig {
  private static Logger LOG = LoggerFactory.getLogger(TestWebSecurityConfig.class);

  /**
   * Configure spring security to enable a simple webform-login + a simple rest login.
   */
  @Override
  public void configure(HttpSecurity http) throws Exception {

    super.configure(http);
    http.addFilterBefore(basicAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    // Disable CSRF protection in tests for simpler testing of REST services
    http.csrf().disable();
    LOG.debug("*** CSRF disabled - this config should only be used in development environment ***");
  }

  /**
   * @return {@link BasicAuthenticationFilter}.
   * @throws Exception on initialization error.
   */
  @Bean
  protected BasicAuthenticationFilter basicAuthenticationFilter() throws Exception {

    AuthenticationEntryPoint authenticationEntryPoint = new BasicAuthenticationEntryPoint();
    BasicAuthenticationFilter basicAuthenticationFilter =
        new BasicAuthenticationFilter(authenticationManagerBean(), authenticationEntryPoint);
    return basicAuthenticationFilter;
  }

}
