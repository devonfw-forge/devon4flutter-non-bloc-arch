package com.example.domain.myapp.employeemanagement.logic.impl.usecase;

import com.example.domain.myapp.employeemanagement.logic.api.to.EmployeeListContentResponseDtoSchemaEto;
import com.example.domain.myapp.employeemanagement.logic.api.usecase.UcFindEmployeeListContentResponseDtoSchema;
import com.example.domain.myapp.employeemanagement.logic.base.usecase.AbstractEmployeeListContentResponseDtoSchemaUc;
import com.example.domain.myapp.employeemanagement.dataaccess.api.EmployeeListContentResponseDtoSchemaEntity;
import com.example.domain.myapp.employeemanagement.logic.api.to.EmployeeListContentResponseDtoSchemaSearchCriteriaTo;
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
 * Use case implementation for searching, filtering and getting EmployeeListContentResponseDtoSchemas
 */
@Named
@Validated
@Transactional
public class UcFindEmployeeListContentResponseDtoSchemaImpl extends AbstractEmployeeListContentResponseDtoSchemaUc implements UcFindEmployeeListContentResponseDtoSchema {

	  /** Logger instance. */
    private static final Logger LOG = LoggerFactory.getLogger(UcFindEmployeeListContentResponseDtoSchemaImpl.class);


    @Override
    public EmployeeListContentResponseDtoSchemaEto findEmployeeListContentResponseDtoSchema(long id) {
      LOG.debug("Get EmployeeListContentResponseDtoSchema with id {} from database.", id);
      Optional<EmployeeListContentResponseDtoSchemaEntity> foundEntity = getEmployeeListContentResponseDtoSchemaRepository().findById(id);
      if (foundEntity.isPresent())
        return getBeanMapper().map(foundEntity.get(), EmployeeListContentResponseDtoSchemaEto.class);
      else
        return null;
    }

    @Override
    public Page<EmployeeListContentResponseDtoSchemaEto> findEmployeeListContentResponseDtoSchemas(EmployeeListContentResponseDtoSchemaSearchCriteriaTo criteria) {
      Page<EmployeeListContentResponseDtoSchemaEntity> employeelistcontentresponsedtoschemas = getEmployeeListContentResponseDtoSchemaRepository().findByCriteria(criteria);
    return mapPaginatedEntityList(employeelistcontentresponsedtoschemas, EmployeeListContentResponseDtoSchemaEto.class);
  }

}
