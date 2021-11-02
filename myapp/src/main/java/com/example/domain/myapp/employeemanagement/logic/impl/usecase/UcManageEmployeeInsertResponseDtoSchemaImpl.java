package com.example.domain.myapp.employeemanagement.logic.impl.usecase;

import com.example.domain.myapp.employeemanagement.logic.api.to.EmployeeInsertResponseDtoSchemaEto;
import com.example.domain.myapp.employeemanagement.logic.api.usecase.UcManageEmployeeInsertResponseDtoSchema;
import com.example.domain.myapp.employeemanagement.logic.base.usecase.AbstractEmployeeInsertResponseDtoSchemaUc;
import com.example.domain.myapp.employeemanagement.dataaccess.api.EmployeeInsertResponseDtoSchemaEntity;
import org.springframework.validation.annotation.Validated;
import java.util.Objects;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use case implementation for modifying and deleting EmployeeInsertResponseDtoSchemas
 */
@Named
@Validated
@Transactional
public class UcManageEmployeeInsertResponseDtoSchemaImpl extends AbstractEmployeeInsertResponseDtoSchemaUc implements UcManageEmployeeInsertResponseDtoSchema {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(UcManageEmployeeInsertResponseDtoSchemaImpl.class);

  @Override
  public boolean deleteEmployeeInsertResponseDtoSchema(long employeeInsertResponseDtoSchemaId) {

    EmployeeInsertResponseDtoSchemaEntity employeeInsertResponseDtoSchema = getEmployeeInsertResponseDtoSchemaRepository().find(employeeInsertResponseDtoSchemaId);
    getEmployeeInsertResponseDtoSchemaRepository().delete(employeeInsertResponseDtoSchema);
    LOG.debug("The employeeInsertResponseDtoSchema with id '{}' has been deleted.", employeeInsertResponseDtoSchemaId);
    return true;
  }

  @Override
  public EmployeeInsertResponseDtoSchemaEto saveEmployeeInsertResponseDtoSchema(EmployeeInsertResponseDtoSchemaEto employeeInsertResponseDtoSchema) {

   Objects.requireNonNull(employeeInsertResponseDtoSchema, "employeeInsertResponseDtoSchema");

	 EmployeeInsertResponseDtoSchemaEntity employeeInsertResponseDtoSchemaEntity = getBeanMapper().map(employeeInsertResponseDtoSchema, EmployeeInsertResponseDtoSchemaEntity.class);

   //initialize, validate employeeInsertResponseDtoSchemaEntity here if necessary
   EmployeeInsertResponseDtoSchemaEntity resultEntity = getEmployeeInsertResponseDtoSchemaRepository().save(employeeInsertResponseDtoSchemaEntity);
   LOG.debug("EmployeeInsertResponseDtoSchema with id '{}' has been created.",resultEntity.getId());
   return getBeanMapper().map(resultEntity, EmployeeInsertResponseDtoSchemaEto.class);
  }
}
