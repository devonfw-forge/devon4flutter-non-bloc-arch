package com.example.domain.myapp;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.util.ReflectionUtils;

import com.devonfw.module.basic.common.api.reference.IdRef;
import com.devonfw.module.basic.common.api.reflect.Devon4jPackage;
import com.devonfw.module.basic.common.api.to.AbstractTo;
import com.devonfw.module.test.common.base.ModuleTest;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.EqualsVerifierApi;
import nl.jqno.equalsverifier.Warning;

public class ToTest extends ModuleTest {

  private static final ToExclusions EXCLUSIONS = new ToExclusions();

  static {
    // EXCLUSIONS.add(MyBusinessEto.class).ignoreEquals().ignoreProperties("foo", "bar");
  }

  /**
   * Finds all {@link AbstractTo Transportobjekte} of this app via reflection and tests all getters/setters as well as equals and hashCode.
   */
  @Test
  public void testTos() {

    List<Class<?>> toClasses = getToClasses();
    SoftAssertions assertion = new SoftAssertions();
    for (Class<?> toClass : toClasses) {
      ToExclusion exclusion = EXCLUSIONS.get(toClass.getName());
      testGetSet(toClass, assertion, exclusion);
      testEqualsAndHashcode(toClass, assertion, exclusion);
    }
    assertion.assertAll();
  }

  /**
   * Checks if a certain class has a custom equals method
   * @param clazz class to be checked
   * @return boolean value if the class to be checked has a custom equals()
   *         method or not
   */
  private boolean hasCustomEquals(Class<?> clazz) {

    try {
      clazz.getDeclaredMethod("equals", new Class[] {});
    } catch (NoSuchMethodException noCustomEquals) {
      return false;
    }

    return true;
  }

  /**
   * Checks if a certain class has a custom hashCode method
   *
   * @param clazz class to be checked
   * @return boolean value if the class to be checked has a custom hashCode()
   *         method or not
   */
  private boolean hasCustomHashCode(Class<?> clazz) {

    try {
      clazz.getDeclaredMethod("hashCode", new Class[] { Object.class });
    } catch (NoSuchMethodException noCustomHashCode) {
      return false;
    }

    return true;
  }

  private void testEqualsAndHashcode(Class<?> clazz, SoftAssertions assertion, ToExclusion exclusion) {

	if(!hasCustomEquals(clazz) && !hasCustomHashCode(clazz)) {
	  return;
	}

    if ((exclusion != null) && (exclusion.ignoreEquals)) {
      return;
    }
    EqualsVerifierApi<?> verifier = EqualsVerifier.forClass(clazz).withRedefinedSuperclass().usingGetClass().suppress(Warning.NONFINAL_FIELDS, Warning.INHERITED_DIRECTLY_FROM_OBJECT);
    try {
      verifier.verify();
    } catch (AssertionError e) {
      assertion.fail(e.getMessage(), e);
    }
  }

  private void testGetSet(Class<?> clazz, SoftAssertions assertion, ToExclusion exclusion) {

    if (!Modifier.isAbstract(clazz.getModifiers())) {
      ReflectionUtils.doWithLocalFields(clazz, field -> {
        try {
          if (isCheckable(clazz, field, exclusion)) {
            Object testInstance = clazz.newInstance();
            Method getMethod = null;
            Method setMethod = null;

            if (Boolean.class.equals(field.getType()) || boolean.class.equals(field.getType())) {
              getMethod = findMethod("is", clazz, field);
              if (getMethod == null) {
                getMethod = findMethod("get", clazz, field);
              }
            } else {
              getMethod = findMethod("get", clazz, field);
            }
            setMethod = findMethod("set", clazz, field);

            if (getMethod == null) {
              assertion.fail("Getter of field " + clazz.getSimpleName() + "." + field.getName() + " has not been found.");
            } else if (setMethod == null) {
              assertion.fail("Setter of field " + clazz.getSimpleName() + "." + field.getName() + " has not been found.");
            } else {
              getMethod.setAccessible(true);
              setMethod.setAccessible(true);

              Object value = createTestObject(field);
              setMethod.invoke(testInstance, value);
              Object value2 = getMethod.invoke(testInstance);
              assertion.assertThat(value2).as("Value from getter of field " + clazz.getSimpleName() + "." + field.getName()).isEqualTo(value);
            }
          }
        } catch (Exception e) {
          assertion.fail("Error with getter/setter of field " + clazz.getSimpleName() + "." + field.getName() + ": "
              + e.getClass().getSimpleName() + ": " + e.getMessage(), e);
        }
      });
    }
  }

  private boolean isCheckable(Class<?> clazz, Field field, ToExclusion exclusion) {

    if (field.getName().startsWith("$") || Modifier.isFinal(field.getModifiers())) {
      return false;
    }
    if (Optional.class.isAssignableFrom(field.getType())) {
      return false;
    }
    if (Modifier.isTransient(field.getModifiers())) {
      return false;
    }
    if ((exclusion != null) && (exclusion.properties.contains(field.getName()))) {
      return false;
    }
    return true;
  }

  private Object createTestObject(Field field) throws InstantiationException, IllegalAccessException {

    Class<?> type = field.getType();
    return createInstance(type);
  }

  private Object createInstance(Class<?> type) throws InstantiationException, IllegalAccessException {

    Object o = null;
    if (type.isEnum()) {
      o = type.getEnumConstants()[0];
    } else if (List.class.isAssignableFrom(type)) {
      o = new ArrayList<>();
    } else if (Set.class.isAssignableFrom(type)) {
      o = new HashSet<>();
    } else if (Map.class.isAssignableFrom(type)) {
      o = new HashMap<>();
    } else if (Integer.class.isAssignableFrom(type) || int.class.isAssignableFrom(type)) {
      o = Integer.valueOf(1);
    } else if (Long.class.isAssignableFrom(type) || long.class.isAssignableFrom(type)) {
      o = Long.valueOf(2);
    } else if (Boolean.class.isAssignableFrom(type) || boolean.class.isAssignableFrom(type)) {
      o = Boolean.TRUE;
    } else if (String.class.isAssignableFrom(type)) {
      o = "hello world";
    } else if (Year.class.isAssignableFrom(type)) {
      o = Year.of(1999);
    } else if (Instant.class.isAssignableFrom(type)) {
      o = Instant.now();
    } else if (Timestamp.class.isAssignableFrom(type)) {
      o = Timestamp.from(Instant.now());
    } else if (type == byte[].class) {
      o = "test".getBytes();
    } else if (Serializable.class.equals(type)) {
      o = Integer.valueOf(1);
    } else if (type == BigDecimal.class) {
      o = BigDecimal.ZERO;
    } else if (type == BigInteger.class) {
      o = BigInteger.ZERO;
    } else if (type == Integer.class) {
      o = Integer.valueOf(0);
    } else if ((type == Long.class) || (type == Number.class)) {
      o = Long.valueOf(0);
    } else if (type == Double.class) {
      o = Double.valueOf(0);
    } else if (type == IdRef.class) {
      o = IdRef.of(0);
    }
    return o;
  }

  private static Method findMethod(String prefix, Class<?> clazz, Field field) {

    String methodname = prefix + field.getName();

    return Arrays.asList(ReflectionUtils.getAllDeclaredMethods(clazz)).stream()
        .filter(m -> m.getName().equalsIgnoreCase(methodname)).findFirst().orElse(null);
  }

  private List<Class<?>> getToClasses() {

    ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
    provider.addIncludeFilter(new AssignableTypeFilter(AbstractTo.class));

    Devon4jPackage pkg = Devon4jPackage.of(ToTest.class);
    Set<BeanDefinition> toSet = provider.findCandidateComponents(pkg.getRoot());
    List<Class<?>> toList = new ArrayList<>(toSet.size());
    for (BeanDefinition beanDefinition : toSet) {
      String className = beanDefinition.getBeanClassName();
      try {
        Class<?> toClass = Class.forName(className);
        toList.add(toClass);
      } catch (ClassNotFoundException e) {
        fail("Class " + className + " could not been found.");
      }
    }
    return toList;
  }

  public static class ToExclusions {

    private final Map<String, ToExclusion> exclusions;

    public ToExclusions() {

      super();
      this.exclusions = new HashMap<>();
    }

    public ToExclusion add(Class<? extends AbstractTo> toClass) {

      return add(toClass.getName());
    }

    public ToExclusion add(String toClassQualifiedName) {

      ToExclusion exclusion = new ToExclusion();
      ToExclusion duplicate = this.exclusions.put(toClassQualifiedName, exclusion);
      if (duplicate != null) {
        throw new IllegalStateException("Doppelte ToExclusion von " + toClassQualifiedName);
      }
      return exclusion;
    }

    public ToExclusion get(String toClassQualifiedName) {

      return this.exclusions.get(toClassQualifiedName);
    }

  }

  public static class ToExclusion {

    private Set<String> properties;

    private boolean ignoreEquals;

    public ToExclusion() {

      super();
      this.properties = new HashSet<>();
      this.ignoreEquals = false;
    }

    /**
     * Ignores the given properties for testing of getters/setters.
     *
     * @return this
     */
    public ToExclusion ignoreProperties(String... props) {

      if (this.properties == null) {
        this.properties = new HashSet<>();
      }
      for (String property : props) {
        this.properties.add(property);
      }
      return this;
    }

    /**
     * Ignores this transfer-object for testing of equals and hashCode with {@link EqualsVerifier}.
     *
     * @return this
     */
    public ToExclusion ignoreEquals() {

      this.ignoreEquals = true;
      return this;
    }
  }
}