package com.example.domain.myapp.employeemanagement.logic.impl.usecase;

import com.example.domain.myapp.employeemanagement.logic.api.to.PageableSortDtoSchemaEto;
import com.example.domain.myapp.employeemanagement.logic.api.usecase.UcFindPageableSortDtoSchema;
import com.example.domain.myapp.employeemanagement.logic.base.usecase.AbstractPageableSortDtoSchemaUc;
import com.example.domain.myapp.employeemanagement.dataaccess.api.PageableSortDtoSchemaEntity;
import com.example.domain.myapp.employeemanagement.logic.api.to.PageableSortDtoSchemaSearchCriteriaTo;
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
 * Use case implementation for searching, filtering and getting PageableSortDtoSchemas
 */
@Named
@Validated
@Transactional
public class UcFindPageableSortDtoSchemaImpl extends AbstractPageableSortDtoSchemaUc implements UcFindPageableSortDtoSchema {

	  /** Logger instance. */
    private static final Logger LOG = LoggerFactory.getLogger(UcFindPageableSortDtoSchemaImpl.class);


    @Override
    public PageableSortDtoSchemaEto findPageableSortDtoSchema(long id) {
      LOG.debug("Get PageableSortDtoSchema with id {} from database.", id);
      Optional<PageableSortDtoSchemaEntity> foundEntity = getPageableSortDtoSchemaRepository().findById(id);
      if (foundEntity.isPresent())
        return getBeanMapper().map(foundEntity.get(), PageableSortDtoSchemaEto.class);
      else
        return null;
    }

    @Override
    public Page<PageableSortDtoSchemaEto> findPageableSortDtoSchemas(PageableSortDtoSchemaSearchCriteriaTo criteria) {
      Page<PageableSortDtoSchemaEntity> pageablesortdtoschemas = getPageableSortDtoSchemaRepository().findByCriteria(criteria);
    return mapPaginatedEntityList(pageablesortdtoschemas, PageableSortDtoSchemaEto.class);
  }

}
