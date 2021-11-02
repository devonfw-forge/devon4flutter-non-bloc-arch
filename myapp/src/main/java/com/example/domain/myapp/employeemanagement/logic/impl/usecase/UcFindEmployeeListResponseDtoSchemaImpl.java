package com.example.domain.myapp.employeemanagement.logic.impl.usecase;

import com.example.domain.myapp.employeemanagement.logic.api.to.EmployeeListResponseDtoSchemaEto;
import com.example.domain.myapp.employeemanagement.logic.api.usecase.UcFindEmployeeListResponseDtoSchema;
import com.example.domain.myapp.employeemanagement.logic.base.usecase.AbstractEmployeeListResponseDtoSchemaUc;
import com.example.domain.myapp.employeemanagement.dataaccess.api.EmployeeListResponseDtoSchemaEntity;
import com.example.domain.myapp.employeemanagement.logic.api.to.EmployeeListResponseDtoSchemaSearchCriteriaTo;
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
 * Use case implementation for searching, filtering and getting EmployeeListResponseDtoSchemas
 */
@Named
@Validated
@Transactional
public class UcFindEmployeeListResponseDtoSchemaImpl extends AbstractEmployeeListResponseDtoSchemaUc implements UcFindEmployeeListResponseDtoSchema {

	  /** Logger instance. */
    private static final Logger LOG = LoggerFactory.getLogger(UcFindEmployeeListResponseDtoSchemaImpl.class);


    @Override
    public EmployeeListResponseDtoSchemaEto findEmployeeListResponseDtoSchema(long id) {
      LOG.debug("Get EmployeeListResponseDtoSchema with id {} from database.", id);
      Optional<EmployeeListResponseDtoSchemaEntity> foundEntity = getEmployeeListResponseDtoSchemaRepository().findById(id);
      if (foundEntity.isPresent())
        return getBeanMapper().map(foundEntity.get(), EmployeeListResponseDtoSchemaEto.class);
      else
        return null;
    }

    @Override
    public Page<EmployeeListResponseDtoSchemaEto> findEmployeeListResponseDtoSchemas(EmployeeListResponseDtoSchemaSearchCriteriaTo criteria) {
      Page<EmployeeListResponseDtoSchemaEntity> employeelistresponsedtoschemas = getEmployeeListResponseDtoSchemaRepository().findByCriteria(criteria);
    return mapPaginatedEntityList(employeelistresponsedtoschemas, EmployeeListResponseDtoSchemaEto.class);
  }

}
