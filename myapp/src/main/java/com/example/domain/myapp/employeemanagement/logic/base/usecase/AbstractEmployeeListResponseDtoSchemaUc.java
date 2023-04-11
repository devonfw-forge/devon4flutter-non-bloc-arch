package com.example.domain.myapp.employeemanagement.logic.base.usecase;

import com.example.domain.myapp.general.logic.base.AbstractUc;
import com.example.domain.myapp.employeemanagement.dataaccess.api.repo.EmployeeListResponseDtoSchemaRepository;

import javax.inject.Inject;

/**
 * Abstract use case for EmployeeListResponseDtoSchemas, which provides access to the commonly necessary data access objects.
 */
public class AbstractEmployeeListResponseDtoSchemaUc extends AbstractUc {

	  /** @see #getEmployeeListResponseDtoSchemaRepository() */
	  @Inject
    private EmployeeListResponseDtoSchemaRepository employeeListResponseDtoSchemaRepository;

    /**
     * Returns the field 'employeeListResponseDtoSchemaRepository'.
     * @return the {@link EmployeeListResponseDtoSchemaRepository} instance.
     */
    public EmployeeListResponseDtoSchemaRepository getEmployeeListResponseDtoSchemaRepository() {

      return this.employeeListResponseDtoSchemaRepository;
    }

}
