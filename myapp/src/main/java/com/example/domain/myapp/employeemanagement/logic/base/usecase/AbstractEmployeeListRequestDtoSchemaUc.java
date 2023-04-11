package com.example.domain.myapp.employeemanagement.logic.base.usecase;

import com.example.domain.myapp.general.logic.base.AbstractUc;
import com.example.domain.myapp.employeemanagement.dataaccess.api.repo.EmployeeListRequestDtoSchemaRepository;

import javax.inject.Inject;

/**
 * Abstract use case for EmployeeListRequestDtoSchemas, which provides access to the commonly necessary data access objects.
 */
public class AbstractEmployeeListRequestDtoSchemaUc extends AbstractUc {

	  /** @see #getEmployeeListRequestDtoSchemaRepository() */
	  @Inject
    private EmployeeListRequestDtoSchemaRepository employeeListRequestDtoSchemaRepository;

    /**
     * Returns the field 'employeeListRequestDtoSchemaRepository'.
     * @return the {@link EmployeeListRequestDtoSchemaRepository} instance.
     */
    public EmployeeListRequestDtoSchemaRepository getEmployeeListRequestDtoSchemaRepository() {

      return this.employeeListRequestDtoSchemaRepository;
    }

}
