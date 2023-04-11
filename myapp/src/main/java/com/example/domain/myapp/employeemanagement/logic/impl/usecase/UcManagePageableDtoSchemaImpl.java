package com.example.domain.myapp.employeemanagement.logic.impl.usecase;

import com.example.domain.myapp.employeemanagement.logic.api.to.PageableDtoSchemaEto;
import com.example.domain.myapp.employeemanagement.logic.api.usecase.UcManagePageableDtoSchema;
import com.example.domain.myapp.employeemanagement.logic.base.usecase.AbstractPageableDtoSchemaUc;
import com.example.domain.myapp.employeemanagement.dataaccess.api.PageableDtoSchemaEntity;
import org.springframework.validation.annotation.Validated;
import java.util.Objects;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use case implementation for modifying and deleting PageableDtoSchemas
 */
@Named
@Validated
@Transactional
public class UcManagePageableDtoSchemaImpl extends AbstractPageableDtoSchemaUc implements UcManagePageableDtoSchema {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(UcManagePageableDtoSchemaImpl.class);

  @Override
  public boolean deletePageableDtoSchema(long pageableDtoSchemaId) {

    PageableDtoSchemaEntity pageableDtoSchema = getPageableDtoSchemaRepository().find(pageableDtoSchemaId);
    getPageableDtoSchemaRepository().delete(pageableDtoSchema);
    LOG.debug("The pageableDtoSchema with id '{}' has been deleted.", pageableDtoSchemaId);
    return true;
  }

  @Override
  public PageableDtoSchemaEto savePageableDtoSchema(PageableDtoSchemaEto pageableDtoSchema) {

   Objects.requireNonNull(pageableDtoSchema, "pageableDtoSchema");

	 PageableDtoSchemaEntity pageableDtoSchemaEntity = getBeanMapper().map(pageableDtoSchema, PageableDtoSchemaEntity.class);

   //initialize, validate pageableDtoSchemaEntity here if necessary
   PageableDtoSchemaEntity resultEntity = getPageableDtoSchemaRepository().save(pageableDtoSchemaEntity);
   LOG.debug("PageableDtoSchema with id '{}' has been created.",resultEntity.getId());
   return getBeanMapper().map(resultEntity, PageableDtoSchemaEto.class);
  }
}
