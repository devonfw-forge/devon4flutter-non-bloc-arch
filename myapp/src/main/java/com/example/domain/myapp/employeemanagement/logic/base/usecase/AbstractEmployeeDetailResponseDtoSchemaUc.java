package com.example.domain.myapp.employeemanagement.logic.base.usecase;

import com.example.domain.myapp.general.logic.base.AbstractUc;
import com.example.domain.myapp.employeemanagement.dataaccess.api.repo.EmployeeDetailResponseDtoSchemaRepository;

import javax.inject.Inject;

/**
 * Abstract use case for EmployeeDetailResponseDtoSchemas, which provides access to the commonly necessary data access objects.
 */
public class AbstractEmployeeDetailResponseDtoSchemaUc extends AbstractUc {

	  /** @see #getEmployeeDetailResponseDtoSchemaRepository() */
	  @Inject
    private EmployeeDetailResponseDtoSchemaRepository employeeDetailResponseDtoSchemaRepository;

    /**
     * Returns the field 'employeeDetailResponseDtoSchemaRepository'.
     * @return the {@link EmployeeDetailResponseDtoSchemaRepository} instance.
     */
    public EmployeeDetailResponseDtoSchemaRepository getEmployeeDetailResponseDtoSchemaRepository() {

      return this.employeeDetailResponseDtoSchemaRepository;
    }

}
