package com.example.domain.myapp.employeemanagement.logic.base.usecase;

import com.example.domain.myapp.general.logic.base.AbstractUc;
import com.example.domain.myapp.employeemanagement.dataaccess.api.repo.EmployeeInsertRequestDtoSchemaRepository;

import javax.inject.Inject;

/**
 * Abstract use case for EmployeeInsertRequestDtoSchemas, which provides access to the commonly necessary data access objects.
 */
public class AbstractEmployeeInsertRequestDtoSchemaUc extends AbstractUc {

	  /** @see #getEmployeeInsertRequestDtoSchemaRepository() */
	  @Inject
    private EmployeeInsertRequestDtoSchemaRepository employeeInsertRequestDtoSchemaRepository;

    /**
     * Returns the field 'employeeInsertRequestDtoSchemaRepository'.
     * @return the {@link EmployeeInsertRequestDtoSchemaRepository} instance.
     */
    public EmployeeInsertRequestDtoSchemaRepository getEmployeeInsertRequestDtoSchemaRepository() {

      return this.employeeInsertRequestDtoSchemaRepository;
    }

}
