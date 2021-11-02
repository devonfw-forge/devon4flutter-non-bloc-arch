package com.example.domain.myapp.employeemanagement.logic.base.usecase;

import com.example.domain.myapp.general.logic.base.AbstractUc;
import com.example.domain.myapp.employeemanagement.dataaccess.api.repo.PageableSortDtoSchemaRepository;

import javax.inject.Inject;

/**
 * Abstract use case for PageableSortDtoSchemas, which provides access to the commonly necessary data access objects.
 */
public class AbstractPageableSortDtoSchemaUc extends AbstractUc {

	  /** @see #getPageableSortDtoSchemaRepository() */
	  @Inject
    private PageableSortDtoSchemaRepository pageableSortDtoSchemaRepository;

    /**
     * Returns the field 'pageableSortDtoSchemaRepository'.
     * @return the {@link PageableSortDtoSchemaRepository} instance.
     */
    public PageableSortDtoSchemaRepository getPageableSortDtoSchemaRepository() {

      return this.pageableSortDtoSchemaRepository;
    }

}
