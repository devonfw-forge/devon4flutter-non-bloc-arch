package com.example.domain.myapp.general.common.base;

import com.devonfw.module.beanmapping.common.api.BeanMapper;

import javax.inject.Inject;

/**
 * This abstract class provides {@link #getBeanMapper() access} to the {@link BeanMapper}.
 */
public abstract class AbstractBeanMapperSupport {

  private BeanMapper beanMapper;

  /**
   * @param beanMapper is the {@link BeanMapper} to {@link Inject}
   */
  @Inject
  public void setBeanMapper(BeanMapper beanMapper) {

    this.beanMapper = beanMapper;
  }

  /**
   * @return the {@link BeanMapper} instance.
   */
  protected BeanMapper getBeanMapper() {

    return this.beanMapper;
  }

}
