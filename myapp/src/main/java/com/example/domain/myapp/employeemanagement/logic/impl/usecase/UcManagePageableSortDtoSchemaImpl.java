package com.example.domain.myapp.employeemanagement.logic.impl.usecase;

import com.example.domain.myapp.employeemanagement.logic.api.to.PageableSortDtoSchemaEto;
import com.example.domain.myapp.employeemanagement.logic.api.usecase.UcManagePageableSortDtoSchema;
import com.example.domain.myapp.employeemanagement.logic.base.usecase.AbstractPageableSortDtoSchemaUc;
import com.example.domain.myapp.employeemanagement.dataaccess.api.PageableSortDtoSchemaEntity;
import org.springframework.validation.annotation.Validated;
import java.util.Objects;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use case implementation for modifying and deleting PageableSortDtoSchemas
 */
@Named
@Validated
@Transactional
public class UcManagePageableSortDtoSchemaImpl extends AbstractPageableSortDtoSchemaUc implements UcManagePageableSortDtoSchema {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(UcManagePageableSortDtoSchemaImpl.class);

  @Override
  public boolean deletePageableSortDtoSchema(long pageableSortDtoSchemaId) {

    PageableSortDtoSchemaEntity pageableSortDtoSchema = getPageableSortDtoSchemaRepository().find(pageableSortDtoSchemaId);
    getPageableSortDtoSchemaRepository().delete(pageableSortDtoSchema);
    LOG.debug("The pageableSortDtoSchema with id '{}' has been deleted.", pageableSortDtoSchemaId);
    return true;
  }

  @Override
  public PageableSortDtoSchemaEto savePageableSortDtoSchema(PageableSortDtoSchemaEto pageableSortDtoSchema) {

   Objects.requireNonNull(pageableSortDtoSchema, "pageableSortDtoSchema");

	 PageableSortDtoSchemaEntity pageableSortDtoSchemaEntity = getBeanMapper().map(pageableSortDtoSchema, PageableSortDtoSchemaEntity.class);

   //initialize, validate pageableSortDtoSchemaEntity here if necessary
   PageableSortDtoSchemaEntity resultEntity = getPageableSortDtoSchemaRepository().save(pageableSortDtoSchemaEntity);
   LOG.debug("PageableSortDtoSchema with id '{}' has been created.",resultEntity.getId());
   return getBeanMapper().map(resultEntity, PageableSortDtoSchemaEto.class);
  }
}
