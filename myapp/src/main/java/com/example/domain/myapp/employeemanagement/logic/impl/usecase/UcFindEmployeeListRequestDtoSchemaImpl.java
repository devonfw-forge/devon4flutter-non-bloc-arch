package com.example.domain.myapp.employeemanagement.logic.impl.usecase;

import com.example.domain.myapp.employeemanagement.logic.api.to.EmployeeListRequestDtoSchemaEto;
import com.example.domain.myapp.employeemanagement.logic.api.usecase.UcFindEmployeeListRequestDtoSchema;
import com.example.domain.myapp.employeemanagement.logic.base.usecase.AbstractEmployeeListRequestDtoSchemaUc;
import com.example.domain.myapp.employeemanagement.dataaccess.api.EmployeeListRequestDtoSchemaEntity;
import com.example.domain.myapp.employeemanagement.logic.api.to.EmployeeListRequestDtoSchemaSearchCriteriaTo;
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
 * Use case implementation for searching, filtering and getting EmployeeListRequestDtoSchemas
 */
@Named
@Validated
@Transactional
public class UcFindEmployeeListRequestDtoSchemaImpl extends AbstractEmployeeListRequestDtoSchemaUc implements UcFindEmployeeListRequestDtoSchema {

	  /** Logger instance. */
    private static final Logger LOG = LoggerFactory.getLogger(UcFindEmployeeListRequestDtoSchemaImpl.class);


    @Override
    public EmployeeListRequestDtoSchemaEto findEmployeeListRequestDtoSchema(long id) {
      LOG.debug("Get EmployeeListRequestDtoSchema with id {} from database.", id);
      Optional<EmployeeListRequestDtoSchemaEntity> foundEntity = getEmployeeListRequestDtoSchemaRepository().findById(id);
      if (foundEntity.isPresent())
        return getBeanMapper().map(foundEntity.get(), EmployeeListRequestDtoSchemaEto.class);
      else
        return null;
    }

    @Override
    public Page<EmployeeListRequestDtoSchemaEto> findEmployeeListRequestDtoSchemas(EmployeeListRequestDtoSchemaSearchCriteriaTo criteria) {
      Page<EmployeeListRequestDtoSchemaEntity> employeelistrequestdtoschemas = getEmployeeListRequestDtoSchemaRepository().findByCriteria(criteria);
    return mapPaginatedEntityList(employeelistrequestdtoschemas, EmployeeListRequestDtoSchemaEto.class);
  }

}
