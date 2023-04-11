package com.example.domain.myapp.employeemanagement.logic.impl.usecase;

import com.example.domain.myapp.employeemanagement.logic.api.to.EmployeeListResponseDtoSchemaEto;
import com.example.domain.myapp.employeemanagement.logic.api.usecase.UcManageEmployeeListResponseDtoSchema;
import com.example.domain.myapp.employeemanagement.logic.base.usecase.AbstractEmployeeListResponseDtoSchemaUc;
import com.example.domain.myapp.employeemanagement.dataaccess.api.EmployeeListResponseDtoSchemaEntity;
import org.springframework.validation.annotation.Validated;
import java.util.Objects;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use case implementation for modifying and deleting EmployeeListResponseDtoSchemas
 */
@Named
@Validated
@Transactional
public class UcManageEmployeeListResponseDtoSchemaImpl extends AbstractEmployeeListResponseDtoSchemaUc implements UcManageEmployeeListResponseDtoSchema {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(UcManageEmployeeListResponseDtoSchemaImpl.class);

  @Override
  public boolean deleteEmployeeListResponseDtoSchema(long employeeListResponseDtoSchemaId) {

    EmployeeListResponseDtoSchemaEntity employeeListResponseDtoSchema = getEmployeeListResponseDtoSchemaRepository().find(employeeListResponseDtoSchemaId);
    getEmployeeListResponseDtoSchemaRepository().delete(employeeListResponseDtoSchema);
    LOG.debug("The employeeListResponseDtoSchema with id '{}' has been deleted.", employeeListResponseDtoSchemaId);
    return true;
  }

  @Override
  public EmployeeListResponseDtoSchemaEto saveEmployeeListResponseDtoSchema(EmployeeListResponseDtoSchemaEto employeeListResponseDtoSchema) {

   Objects.requireNonNull(employeeListResponseDtoSchema, "employeeListResponseDtoSchema");

	 EmployeeListResponseDtoSchemaEntity employeeListResponseDtoSchemaEntity = getBeanMapper().map(employeeListResponseDtoSchema, EmployeeListResponseDtoSchemaEntity.class);

   //initialize, validate employeeListResponseDtoSchemaEntity here if necessary
   EmployeeListResponseDtoSchemaEntity resultEntity = getEmployeeListResponseDtoSchemaRepository().save(employeeListResponseDtoSchemaEntity);
   LOG.debug("EmployeeListResponseDtoSchema with id '{}' has been created.",resultEntity.getId());
   return getBeanMapper().map(resultEntity, EmployeeListResponseDtoSchemaEto.class);
  }
}
