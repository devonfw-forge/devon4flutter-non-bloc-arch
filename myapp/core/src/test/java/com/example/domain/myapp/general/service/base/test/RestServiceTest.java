package com.example.domain.myapp.general.service.base.test;

import javax.inject.Inject;

import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.example.domain.myapp.SpringBootApp;
import com.example.domain.myapp.general.common.base.test.DbTestHelper;
import com.example.domain.myapp.general.common.base.test.TestUtil;

import com.devonfw.module.test.common.base.SubsystemTest;
import com.devonfw.module.test.common.base.SubsystemDbTest;
import com.devonfw.module.service.common.api.client.ServiceClientFactory;

/**
 * Abstract base class for {@link SubsystemTest}s which runs the tests within a local server. <br/>
 * <br/>
 * The local server's port is randomly assigned.
 */
@SpringBootTest(classes = { SpringBootApp.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class RestServiceTest extends SubsystemDbTest {

  /**
   * The port of the web server during the test.
   */
  @LocalServerPort
  protected int port;

  @Inject
  private ServiceClientFactory serviceClientFactory;

  @Inject
  private DbTestHelper dbTestHelper;

  @Override
  protected void doSetUp() {

    super.doSetUp();
  }

  @Override
  protected void doTearDown() {

    super.doTearDown();
    TestUtil.logout();
  }

  /**
   * @return the {@link DbTestHelper}
   */
  protected DbTestHelper getDbTestHelper() {

    return this.dbTestHelper;
  }


  /**
   * @return the {@link ServiceClientFactory}
   */
  protected ServiceClientFactory getServiceClientFactory() {

    return this.serviceClientFactory;
  }

}
