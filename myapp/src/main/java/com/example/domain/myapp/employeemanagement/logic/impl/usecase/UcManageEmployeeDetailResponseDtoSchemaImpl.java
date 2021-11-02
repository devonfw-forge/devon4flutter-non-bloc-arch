package com.example.domain.myapp.employeemanagement.logic.impl.usecase;

import com.example.domain.myapp.employeemanagement.logic.api.to.EmployeeDetailResponseDtoSchemaEto;
import com.example.domain.myapp.employeemanagement.logic.api.usecase.UcManageEmployeeDetailResponseDtoSchema;
import com.example.domain.myapp.employeemanagement.logic.base.usecase.AbstractEmployeeDetailResponseDtoSchemaUc;
import com.example.domain.myapp.employeemanagement.dataaccess.api.EmployeeDetailResponseDtoSchemaEntity;
import org.springframework.validation.annotation.Validated;
import java.util.Objects;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use case implementation for modifying and deleting EmployeeDetailResponseDtoSchemas
 */
@Named
@Validated
@Transactional
public class UcManageEmployeeDetailResponseDtoSchemaImpl extends AbstractEmployeeDetailResponseDtoSchemaUc implements UcManageEmployeeDetailResponseDtoSchema {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(UcManageEmployeeDetailResponseDtoSchemaImpl.class);

  @Override
  public boolean deleteEmployeeDetailResponseDtoSchema(long employeeDetailResponseDtoSchemaId) {

    EmployeeDetailResponseDtoSchemaEntity employeeDetailResponseDtoSchema = getEmployeeDetailResponseDtoSchemaRepository().find(employeeDetailResponseDtoSchemaId);
    getEmployeeDetailResponseDtoSchemaRepository().delete(employeeDetailResponseDtoSchema);
    LOG.debug("The employeeDetailResponseDtoSchema with id '{}' has been deleted.", employeeDetailResponseDtoSchemaId);
    return true;
  }

  @Override
  public EmployeeDetailResponseDtoSchemaEto saveEmployeeDetailResponseDtoSchema(EmployeeDetailResponseDtoSchemaEto employeeDetailResponseDtoSchema) {

   Objects.requireNonNull(employeeDetailResponseDtoSchema, "employeeDetailResponseDtoSchema");

	 EmployeeDetailResponseDtoSchemaEntity employeeDetailResponseDtoSchemaEntity = getBeanMapper().map(employeeDetailResponseDtoSchema, EmployeeDetailResponseDtoSchemaEntity.class);

   //initialize, validate employeeDetailResponseDtoSchemaEntity here if necessary
   EmployeeDetailResponseDtoSchemaEntity resultEntity = getEmployeeDetailResponseDtoSchemaRepository().save(employeeDetailResponseDtoSchemaEntity);
   LOG.debug("EmployeeDetailResponseDtoSchema with id '{}' has been created.",resultEntity.getId());
   return getBeanMapper().map(resultEntity, EmployeeDetailResponseDtoSchemaEto.class);
  }
}
