package com.example.domain.myapp.general.common.base;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import com.devonfw.module.basic.common.api.to.AbstractEto;
import com.devonfw.module.beanmapping.common.api.BeanMapper;

import com.example.domain.myapp.general.common.base.test.ApplicationComponentTest;
import com.example.domain.myapp.general.dataaccess.api.ApplicationPersistenceEntity;

/**
 * This test verifies that {@link com.example.domain.myapp.SpringBootApp} is able to startup.
 */
public class ApplicationTest extends ApplicationComponentTest {

  @Inject
  private BeanMapper beanMapper;

  /** Test that {@link it.pkg.SpringBootApp} is able to startup. */
  @Test
  public void testContextLoads() {

    // given
    Long id = Long.valueOf(4711);
    MyEntity entity = new MyEntity();
    entity.setId(id);
    // when
    MyEto eto = this.beanMapper.map(entity, MyEto.class);
    // then
    assertThat(eto.getId()).isEqualTo(id);
    assertThat(eto.getModificationCounter()).isEqualTo(0);
    // and when
    entity.setModificationCounter(5);
    // then
    assertThat(eto.getModificationCounter()).isEqualTo(5);
  }

  /** Dummy entity for testing. */
  public static class MyEntity extends ApplicationPersistenceEntity {
    private static final long serialVersionUID = 1L;
  }

  /** Dummy ETO for testing. */
  public static class MyEto extends AbstractEto {
    private static final long serialVersionUID = 1L;
  }
}