package com.example.domain.myapp.general.service.impl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.devonfw.module.logging.common.api.DiagnosticContextFacade;
import com.devonfw.module.logging.common.impl.DiagnosticContextFacadeImpl;
import com.devonfw.module.logging.common.impl.DiagnosticContextFilter;
import com.devonfw.module.logging.common.impl.PerformanceLogFilter;
import com.devonfw.module.service.common.api.constants.ServiceConstants;
import org.springframework.security.config.core.GrantedAuthorityDefaults;

/**
 * Registers a number of filters for web requests.
 */
@Configuration
public class WebConfig {

  private @Autowired AutowireCapableBeanFactory beanFactory;

  /**
   * @return the {@link FilterRegistrationBean} to register the {@link PerformanceLogFilter} that will log all requests
   *         with their duration and status code.
   */
  @Bean
  public FilterRegistrationBean<PerformanceLogFilter> performanceLogFilter() {

    FilterRegistrationBean<PerformanceLogFilter> registration = new FilterRegistrationBean<>();
    PerformanceLogFilter performanceLogFilter = new PerformanceLogFilter();
    this.beanFactory.autowireBean(performanceLogFilter);
    registration.setFilter(performanceLogFilter);
    registration.addUrlPatterns("/*");
    return registration;
  }

  /**
   * @return the {@link DiagnosticContextFacade} implementation.
   */
  @Bean(name = "DiagnosticContextFacade")
  public DiagnosticContextFacade diagnosticContextFacade() {

    return new DiagnosticContextFacadeImpl();
  }

  /**
   * @return the {@link FilterRegistrationBean} to register the {@link DiagnosticContextFilter} that adds the
   *         correlation id as MDC so it will be included in all associated logs.
   */
  @Bean
  public FilterRegistrationBean<DiagnosticContextFilter> diagnosticContextFilter() {

    FilterRegistrationBean<DiagnosticContextFilter> registration = new FilterRegistrationBean<>();
    DiagnosticContextFilter diagnosticContextFilter = new DiagnosticContextFilter();
    this.beanFactory.autowireBean(diagnosticContextFilter);
    registration.setFilter(diagnosticContextFilter);
    registration.addUrlPatterns(ServiceConstants.URL_PATH_SERVICES + "/*");
    return registration;
  }

  /**
   * @return the {@link FilterRegistrationBean} to register the {@link CharacterEncodingFilter} to set the encoding.
   */
  @Bean
  public FilterRegistrationBean<CharacterEncodingFilter> setCharacterEncodingFilter() {

    FilterRegistrationBean<CharacterEncodingFilter> registration = new FilterRegistrationBean<>();
    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
    characterEncodingFilter.setEncoding("UTF-8");
    characterEncodingFilter.setForceEncoding(false);
    this.beanFactory.autowireBean(characterEncodingFilter);
    registration.setFilter(characterEncodingFilter);
    registration.addUrlPatterns("/*");
    return registration;
  }

  /**
   * @return the {@link GrantedAuthorityDefaults} to configure the "role prefix" to the empty string. By default
   *         spring-security is magically adding a strange prefix called "ROLE_" to your granted authorities. In order
   *         to prevent this we use this class with an empty prefix.
   */
  @Bean
  public GrantedAuthorityDefaults grantedAuthorityDefaults() {

    return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
  }
}
