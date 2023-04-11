package com.example.domain.myapp.employeemanagement.logic.base.usecase;

import com.example.domain.myapp.general.logic.base.AbstractUc;
import com.example.domain.myapp.employeemanagement.dataaccess.api.repo.PageableDtoSchemaRepository;

import javax.inject.Inject;

/**
 * Abstract use case for PageableDtoSchemas, which provides access to the commonly necessary data access objects.
 */
public class AbstractPageableDtoSchemaUc extends AbstractUc {

	  /** @see #getPageableDtoSchemaRepository() */
	  @Inject
    private PageableDtoSchemaRepository pageableDtoSchemaRepository;

    /**
     * Returns the field 'pageableDtoSchemaRepository'.
     * @return the {@link PageableDtoSchemaRepository} instance.
     */
    public PageableDtoSchemaRepository getPageableDtoSchemaRepository() {

      return this.pageableDtoSchemaRepository;
    }

}
