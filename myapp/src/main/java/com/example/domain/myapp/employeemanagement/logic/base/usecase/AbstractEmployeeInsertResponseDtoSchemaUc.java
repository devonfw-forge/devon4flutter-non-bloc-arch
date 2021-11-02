package com.example.domain.myapp.employeemanagement.logic.base.usecase;

import com.example.domain.myapp.general.logic.base.AbstractUc;
import com.example.domain.myapp.employeemanagement.dataaccess.api.repo.EmployeeInsertResponseDtoSchemaRepository;

import javax.inject.Inject;

/**
 * Abstract use case for EmployeeInsertResponseDtoSchemas, which provides access to the commonly necessary data access objects.
 */
public class AbstractEmployeeInsertResponseDtoSchemaUc extends AbstractUc {

	  /** @see #getEmployeeInsertResponseDtoSchemaRepository() */
	  @Inject
    private EmployeeInsertResponseDtoSchemaRepository employeeInsertResponseDtoSchemaRepository;

    /**
     * Returns the field 'employeeInsertResponseDtoSchemaRepository'.
     * @return the {@link EmployeeInsertResponseDtoSchemaRepository} instance.
     */
    public EmployeeInsertResponseDtoSchemaRepository getEmployeeInsertResponseDtoSchemaRepository() {

      return this.employeeInsertResponseDtoSchemaRepository;
    }

}
