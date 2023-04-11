package com.example.domain.myapp.general.dataaccess.api;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.example.domain.myapp.general.common.api.ApplicationEntity;
import com.devonfw.module.basic.common.api.entity.PersistenceEntity;

/**
 * Abstract base class for all {@link PersistenceEntity persistence entities} with an {@link #getId() id} and a
 * {@link #getModificationCounter() modificationCounter} (version) field. All persistence entities of this application
 * should inherit from this class. It is using JPA annotations at the getters what has several advantages but also
 * implies that you have to annotate transient getter methods with the {@link Transient} annotation.
 */
@MappedSuperclass
public abstract class ApplicationPersistenceEntity implements ApplicationEntity, PersistenceEntity<Long> {

  private static final long serialVersionUID = 1L;

  private Long id;

  private int modificationCounter;

  /**
   * The constructor.
   */
  public ApplicationPersistenceEntity() {

    super();
  }

  @Override
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {

    return this.id;
  }

  @Override
  public void setId(Long id) {

    this.id = id;
  }

  @Override
  @Version
  public int getModificationCounter() {

    return this.modificationCounter;
  }

  @Override
  public void setModificationCounter(int modificationCounter) {

    this.modificationCounter = modificationCounter;
  }

  @Override
  public String toString() {

    StringBuilder buffer = new StringBuilder();
    toString(buffer);
    return buffer.toString();
  }

  /**
   * Method to extend {@link #toString()} logic.
   *
   * @param buffer is the {@link StringBuilder} where to {@link StringBuilder#append(Object) append} the string
   *        representation.
   */
  protected void toString(StringBuilder buffer) {

    buffer.append(getClass().getSimpleName());
    if (this.id != null) {
      buffer.append("[id=");
      buffer.append(this.id);
      buffer.append("]");
    }
  }
}
