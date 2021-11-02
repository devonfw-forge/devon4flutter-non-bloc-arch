package com.example.domain.myapp.employeemanagement.logic.impl.usecase;

import com.example.domain.myapp.employeemanagement.logic.api.to.EmployeeListRequestDtoSchemaEto;
import com.example.domain.myapp.employeemanagement.logic.api.usecase.UcManageEmployeeListRequestDtoSchema;
import com.example.domain.myapp.employeemanagement.logic.base.usecase.AbstractEmployeeListRequestDtoSchemaUc;
import com.example.domain.myapp.employeemanagement.dataaccess.api.EmployeeListRequestDtoSchemaEntity;
import org.springframework.validation.annotation.Validated;
import java.util.Objects;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use case implementation for modifying and deleting EmployeeListRequestDtoSchemas
 */
@Named
@Validated
@Transactional
public class UcManageEmployeeListRequestDtoSchemaImpl extends AbstractEmployeeListRequestDtoSchemaUc implements UcManageEmployeeListRequestDtoSchema {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(UcManageEmployeeListRequestDtoSchemaImpl.class);

  @Override
  public boolean deleteEmployeeListRequestDtoSchema(long employeeListRequestDtoSchemaId) {

    EmployeeListRequestDtoSchemaEntity employeeListRequestDtoSchema = getEmployeeListRequestDtoSchemaRepository().find(employeeListRequestDtoSchemaId);
    getEmployeeListRequestDtoSchemaRepository().delete(employeeListRequestDtoSchema);
    LOG.debug("The employeeListRequestDtoSchema with id '{}' has been deleted.", employeeListRequestDtoSchemaId);
    return true;
  }

  @Override
  public EmployeeListRequestDtoSchemaEto saveEmployeeListRequestDtoSchema(EmployeeListRequestDtoSchemaEto employeeListRequestDtoSchema) {

   Objects.requireNonNull(employeeListRequestDtoSchema, "employeeListRequestDtoSchema");

	 EmployeeListRequestDtoSchemaEntity employeeListRequestDtoSchemaEntity = getBeanMapper().map(employeeListRequestDtoSchema, EmployeeListRequestDtoSchemaEntity.class);

   //initialize, validate employeeListRequestDtoSchemaEntity here if necessary
   EmployeeListRequestDtoSchemaEntity resultEntity = getEmployeeListRequestDtoSchemaRepository().save(employeeListRequestDtoSchemaEntity);
   LOG.debug("EmployeeListRequestDtoSchema with id '{}' has been created.",resultEntity.getId());
   return getBeanMapper().map(resultEntity, EmployeeListRequestDtoSchemaEto.class);
  }
}
