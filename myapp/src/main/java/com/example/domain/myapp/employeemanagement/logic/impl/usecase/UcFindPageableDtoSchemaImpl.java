package com.example.domain.myapp.employeemanagement.logic.impl.usecase;

import com.example.domain.myapp.employeemanagement.logic.api.to.PageableDtoSchemaEto;
import com.example.domain.myapp.employeemanagement.logic.api.usecase.UcFindPageableDtoSchema;
import com.example.domain.myapp.employeemanagement.logic.base.usecase.AbstractPageableDtoSchemaUc;
import com.example.domain.myapp.employeemanagement.dataaccess.api.PageableDtoSchemaEntity;
import com.example.domain.myapp.employeemanagement.logic.api.to.PageableDtoSchemaSearchCriteriaTo;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import java.util.List;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use case implementation for searching, filtering and getting PageableDtoSchemas
 */
@Named
@Validated
@Transactional
public class UcFindPageableDtoSchemaImpl extends AbstractPageableDtoSchemaUc implements UcFindPageableDtoSchema {

	  /** Logger instance. */
    private static final Logger LOG = LoggerFactory.getLogger(UcFindPageableDtoSchemaImpl.class);


    @Override
    public PageableDtoSchemaEto findPageableDtoSchema(long id) {
      LOG.debug("Get PageableDtoSchema with id {} from database.", id);
      Optional<PageableDtoSchemaEntity> foundEntity = getPageableDtoSchemaRepository().findById(id);
      if (foundEntity.isPresent())
        return getBeanMapper().map(foundEntity.get(), PageableDtoSchemaEto.class);
      else
        return null;
    }

    @Override
    public Page<PageableDtoSchemaEto> findPageableDtoSchemas(PageableDtoSchemaSearchCriteriaTo criteria) {
      Page<PageableDtoSchemaEntity> pageabledtoschemas = getPageableDtoSchemaRepository().findByCriteria(criteria);
    return mapPaginatedEntityList(pageabledtoschemas, PageableDtoSchemaEto.class);
  }

}
