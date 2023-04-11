package com.example.domain.myapp.general.logic.base;

import com.example.domain.myapp.general.common.base.AbstractBeanMapperSupport;

import com.devonfw.module.basic.common.api.entity.GenericEntity;
import com.devonfw.module.basic.common.api.to.AbstractEto;
import com.devonfw.module.basic.common.api.to.MasterCto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract base class for implementations of <em>business logic</em> in this application. Actual implementations need
 * to be annotated with {@link javax.inject.Named}.
 *
 * @see AbstractUc
 * @see AbstractComponentFacade
 */
public abstract class AbstractLogic extends AbstractBeanMapperSupport {

  /**
   * The constructor.
   */
  public AbstractLogic() {

    super();
  }

  /**
   * Creates a {@link Map} with all {@link MasterCto}s from the given {@link Collection} using their
   * {@link MasterCto#getEto() main ETO} {@link AbstractEto#getId() ID} as key. All {@link AbstractEto ETO}s being
   * {@code null} or without an {@link AbstractEto#getId() ID} will be ignored.
   *
   * @param <C> is the generic type of the {@link MasterCto}s.
   * @param entities is the {@link Collection} of {@link MasterCto}s.
   * @return a {@link Map} mapping from {@link AbstractEto#getId() ID} to {@link MasterCto}.
   */
  protected static <C extends MasterCto<?>> Map<Long, C> getCtoMap(Collection<C> ctos) {

    Map<Long, C> id2CtoMap = new HashMap<>();
    for (C cto : ctos) {
      AbstractEto eto = cto.getEto();
      if (eto != null) {
        Long id = eto.getId();
        if (id != null) {
          id2CtoMap.put(id, cto);
        }
      }
    }
    return id2CtoMap;
  }

  /**
   * Creates a {@link Map} with all {@link GenericEntity entities} from the given {@link Collection} using their
   * {@link GenericEntity#getId() ID} as key. All {@link GenericEntity entities} without an
   * {@link GenericEntity#getId() ID} ({@code null}) will be ignored.
   *
   * @param <ID> is the generic type of the {@link GenericEntity#getId() ID}.
   * @param <E> is the generic type of the {@link GenericEntity entity}.
   * @param entities is the {@link Collection} of {@link GenericEntity entities}.
   * @return a {@link Map} mapping from {@link GenericEntity#getId() ID} to {@link GenericEntity entity}.
   */
  protected static <ID, E extends GenericEntity<ID>> Map<ID, E> getEntityMap(Collection<E> entities) {

    Map<ID, E> id2EntityMap = new HashMap<>();
    for (E entity : entities) {
      ID id = entity.getId();
      if (id != null) {
        id2EntityMap.put(id, entity);
      }
    }
    return id2EntityMap;
  }

  /**
   * Determines the {@link GenericEntity entities} to delete if <code>currentList</code> is the current list from the
   * persistence and <code>listToSave</code> is the new list that shall be saved. In other words this method selects the
   * {@link GenericEntity entities} from <code>currentList</code> that are not contained in <code>listToSave</code>.
   *
   * @param <ID> is the generic type of the {@link GenericEntity#getId() ID}.
   * @param <E> is the generic type of the {@link GenericEntity entity}.
   * @param currentEntities is the {@link Collection} of the {@link GenericEntity entities} currently persisted. We
   *        assume that all objects in this list have an {@link GenericEntity#getId() ID} value (that is not
   *        {@code null}).
   * @param entitiesToSave is the {@link Collection} that contains the {@link GenericEntity entities} that shall be
   *        saved. It may contain {@link GenericEntity entities} that have no {@link GenericEntity#getId() ID} that
   *        shall be newly created.
   * @return the {@link List} with the {@link GenericEntity entities} to delete.
   */
  protected static <ID, E extends GenericEntity<ID>> List<E> getEntities2Delete(Collection<E> currentEntities,
      Collection<E> entitiesToSave) {

    List<E> result = new ArrayList<>(currentEntities.size());
    Map<ID, E> entityMap = getEntityMap(entitiesToSave);
    for (E entity : currentEntities) {
      if (!entityMap.containsKey(entity.getId())) {
        // entity from currentList is not contained in listToSave...
        result.add(entity);
      }
    }
    return result;
  }

}
