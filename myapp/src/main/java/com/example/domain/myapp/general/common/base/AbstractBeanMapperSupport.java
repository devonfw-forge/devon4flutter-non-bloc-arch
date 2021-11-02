package com.example.domain.myapp.general.common.base;

import com.devonfw.module.beanmapping.common.api.BeanMapper;

import javax.inject.Inject;

/**
 * This abstract class wraps advanced functionality according dozer mappings
 */
public abstract class AbstractBeanMapperSupport {

  /** @see #getBeanMapper() */
  @Inject
  private BeanMapper beanMapper;

  /**
   * @return the {@link BeanMapper} instance.
   */
  protected BeanMapper getBeanMapper() {

    return this.beanMapper;
  }

}
