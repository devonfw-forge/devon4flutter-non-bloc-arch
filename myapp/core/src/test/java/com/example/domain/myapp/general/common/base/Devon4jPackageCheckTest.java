package com.example.domain.myapp.general.common.base;

import java.util.HashSet;
import java.util.Set;

import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import com.devonfw.module.basic.common.api.reflect.Devon4jPackage;
import com.devonfw.module.test.common.base.ModuleTest;

/**
 * This test verifies that the entire code of your code-base is located in {@link Devon4jPackage#isValid() valid Devon4j
 * packages}.
 */
public class Devon4jPackageCheckTest extends ModuleTest {

  /**
   * Scans all the packages of this application root pacakge namespace. Will verify that these are
   * {@link Devon4jPackage#isValid() valid Devon4j packages}.
   */
  @Test
  public void testPackages() {

    Devon4jPackage pkg = Devon4jPackage.of(Devon4jPackageCheckTest.class);
    assertThat(pkg.isValid()).isTrue();

    ReflectionUtil ru = ReflectionUtilImpl.getInstance();
    Set<String> classNames = ru.findClassNames(getRootPackage2Scan(pkg), true);
    String appPackage = pkg.getRoot() + "." + pkg.getApplication();
    Set<String> packages = new HashSet<>(128);
    packages.add(appPackage); // allow SpringBootApp, etc. in application package
    SoftAssertions assertion = new SoftAssertions();
    for (String className : classNames) {
      int lastDot = className.lastIndexOf('.');
      if (lastDot > 0) {
        String packageName = className.substring(0, lastDot);
        boolean added = packages.add(packageName);
        if (added) {
          pkg = Devon4jPackage.of(packageName);
          if (!pkg.isValid()) {
            assertion.assertThat(pkg.isValid())
                .as("package " + packageName + " is invalid (component: " + pkg.getComponent() + ", layer: "
                    + pkg.getLayer() + ", scope: " + pkg.getScope() + "). Hint contains e.g. "
                    + className.substring(lastDot + 1))
                .isTrue();
          }
        }
      }
    }
    assertion.assertAll();
  }

  /**
   * @param pkg the {@link Devon4jPackage} of this test.
   * @return the root package to scan for {@link Class}es to get the actual packages to check.
   */
  protected String getRootPackage2Scan(Devon4jPackage pkg) {

    return pkg.getRoot() + "." + pkg.getApplication();
  }

}
