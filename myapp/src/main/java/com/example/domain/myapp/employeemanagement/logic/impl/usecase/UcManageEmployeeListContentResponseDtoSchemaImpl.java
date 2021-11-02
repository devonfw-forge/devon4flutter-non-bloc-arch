package com.example.domain.myapp.employeemanagement.logic.impl.usecase;

import com.example.domain.myapp.employeemanagement.logic.api.to.EmployeeListContentResponseDtoSchemaEto;
import com.example.domain.myapp.employeemanagement.logic.api.usecase.UcManageEmployeeListContentResponseDtoSchema;
import com.example.domain.myapp.employeemanagement.logic.base.usecase.AbstractEmployeeListContentResponseDtoSchemaUc;
import com.example.domain.myapp.employeemanagement.dataaccess.api.EmployeeListContentResponseDtoSchemaEntity;
import org.springframework.validation.annotation.Validated;
import java.util.Objects;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use case implementation for modifying and deleting EmployeeListContentResponseDtoSchemas
 */
@Named
@Validated
@Transactional
public class UcManageEmployeeListContentResponseDtoSchemaImpl extends AbstractEmployeeListContentResponseDtoSchemaUc implements UcManageEmployeeListContentResponseDtoSchema {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(UcManageEmployeeListContentResponseDtoSchemaImpl.class);

  @Override
  public boolean deleteEmployeeListContentResponseDtoSchema(long employeeListContentResponseDtoSchemaId) {

    EmployeeListContentResponseDtoSchemaEntity employeeListContentResponseDtoSchema = getEmployeeListContentResponseDtoSchemaRepository().find(employeeListContentResponseDtoSchemaId);
    getEmployeeListContentResponseDtoSchemaRepository().delete(employeeListContentResponseDtoSchema);
    LOG.debug("The employeeListContentResponseDtoSchema with id '{}' has been deleted.", employeeListContentResponseDtoSchemaId);
    return true;
  }

  @Override
  public EmployeeListContentResponseDtoSchemaEto saveEmployeeListContentResponseDtoSchema(EmployeeListContentResponseDtoSchemaEto employeeListContentResponseDtoSchema) {

   Objects.requireNonNull(employeeListContentResponseDtoSchema, "employeeListContentResponseDtoSchema");

	 EmployeeListContentResponseDtoSchemaEntity employeeListContentResponseDtoSchemaEntity = getBeanMapper().map(employeeListContentResponseDtoSchema, EmployeeListContentResponseDtoSchemaEntity.class);

   //initialize, validate employeeListContentResponseDtoSchemaEntity here if necessary
   EmployeeListContentResponseDtoSchemaEntity resultEntity = getEmployeeListContentResponseDtoSchemaRepository().save(employeeListContentResponseDtoSchemaEntity);
   LOG.debug("EmployeeListContentResponseDtoSchema with id '{}' has been created.",resultEntity.getId());
   return getBeanMapper().map(resultEntity, EmployeeListContentResponseDtoSchemaEto.class);
  }
}
