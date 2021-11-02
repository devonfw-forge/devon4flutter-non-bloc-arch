package com.example.domain.myapp.employeemanagement.logic.base.usecase;

import com.example.domain.myapp.general.logic.base.AbstractUc;
import com.example.domain.myapp.employeemanagement.dataaccess.api.repo.EmployeeListContentResponseDtoSchemaRepository;

import javax.inject.Inject;

/**
 * Abstract use case for EmployeeListContentResponseDtoSchemas, which provides access to the commonly necessary data access objects.
 */
public class AbstractEmployeeListContentResponseDtoSchemaUc extends AbstractUc {

	  /** @see #getEmployeeListContentResponseDtoSchemaRepository() */
	  @Inject
    private EmployeeListContentResponseDtoSchemaRepository employeeListContentResponseDtoSchemaRepository;

    /**
     * Returns the field 'employeeListContentResponseDtoSchemaRepository'.
     * @return the {@link EmployeeListContentResponseDtoSchemaRepository} instance.
     */
    public EmployeeListContentResponseDtoSchemaRepository getEmployeeListContentResponseDtoSchemaRepository() {

      return this.employeeListContentResponseDtoSchemaRepository;
    }

}
