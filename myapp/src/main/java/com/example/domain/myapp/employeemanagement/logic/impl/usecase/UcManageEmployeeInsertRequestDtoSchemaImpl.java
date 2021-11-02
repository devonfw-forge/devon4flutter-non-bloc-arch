package com.example.domain.myapp.employeemanagement.logic.impl.usecase;

import com.example.domain.myapp.employeemanagement.logic.api.to.EmployeeInsertRequestDtoSchemaEto;
import com.example.domain.myapp.employeemanagement.logic.api.usecase.UcManageEmployeeInsertRequestDtoSchema;
import com.example.domain.myapp.employeemanagement.logic.base.usecase.AbstractEmployeeInsertRequestDtoSchemaUc;
import com.example.domain.myapp.employeemanagement.dataaccess.api.EmployeeInsertRequestDtoSchemaEntity;
import org.springframework.validation.annotation.Validated;
import java.util.Objects;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use case implementation for modifying and deleting EmployeeInsertRequestDtoSchemas
 */
@Named
@Validated
@Transactional
public class UcManageEmployeeInsertRequestDtoSchemaImpl extends AbstractEmployeeInsertRequestDtoSchemaUc implements UcManageEmployeeInsertRequestDtoSchema {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(UcManageEmployeeInsertRequestDtoSchemaImpl.class);

  @Override
  public boolean deleteEmployeeInsertRequestDtoSchema(long employeeInsertRequestDtoSchemaId) {

    EmployeeInsertRequestDtoSchemaEntity employeeInsertRequestDtoSchema = getEmployeeInsertRequestDtoSchemaRepository().find(employeeInsertRequestDtoSchemaId);
    getEmployeeInsertRequestDtoSchemaRepository().delete(employeeInsertRequestDtoSchema);
    LOG.debug("The employeeInsertRequestDtoSchema with id '{}' has been deleted.", employeeInsertRequestDtoSchemaId);
    return true;
  }

  @Override
  public EmployeeInsertRequestDtoSchemaEto saveEmployeeInsertRequestDtoSchema(EmployeeInsertRequestDtoSchemaEto employeeInsertRequestDtoSchema) {

   Objects.requireNonNull(employeeInsertRequestDtoSchema, "employeeInsertRequestDtoSchema");

	 EmployeeInsertRequestDtoSchemaEntity employeeInsertRequestDtoSchemaEntity = getBeanMapper().map(employeeInsertRequestDtoSchema, EmployeeInsertRequestDtoSchemaEntity.class);

   //initialize, validate employeeInsertRequestDtoSchemaEntity here if necessary
   EmployeeInsertRequestDtoSchemaEntity resultEntity = getEmployeeInsertRequestDtoSchemaRepository().save(employeeInsertRequestDtoSchemaEntity);
   LOG.debug("EmployeeInsertRequestDtoSchema with id '{}' has been created.",resultEntity.getId());
   return getBeanMapper().map(resultEntity, EmployeeInsertRequestDtoSchemaEto.class);
  }
}
