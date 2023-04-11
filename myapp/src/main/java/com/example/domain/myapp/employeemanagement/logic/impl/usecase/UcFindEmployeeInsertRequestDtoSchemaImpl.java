package com.example.domain.myapp.employeemanagement.logic.impl.usecase;

import com.example.domain.myapp.employeemanagement.logic.api.to.EmployeeInsertRequestDtoSchemaEto;
import com.example.domain.myapp.employeemanagement.logic.api.usecase.UcFindEmployeeInsertRequestDtoSchema;
import com.example.domain.myapp.employeemanagement.logic.base.usecase.AbstractEmployeeInsertRequestDtoSchemaUc;
import com.example.domain.myapp.employeemanagement.dataaccess.api.EmployeeInsertRequestDtoSchemaEntity;
import com.example.domain.myapp.employeemanagement.logic.api.to.EmployeeInsertRequestDtoSchemaSearchCriteriaTo;
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
 * Use case implementation for searching, filtering and getting EmployeeInsertRequestDtoSchemas
 */
@Named
@Validated
@Transactional
public class UcFindEmployeeInsertRequestDtoSchemaImpl extends AbstractEmployeeInsertRequestDtoSchemaUc implements UcFindEmployeeInsertRequestDtoSchema {

	  /** Logger instance. */
    private static final Logger LOG = LoggerFactory.getLogger(UcFindEmployeeInsertRequestDtoSchemaImpl.class);


    @Override
    public EmployeeInsertRequestDtoSchemaEto findEmployeeInsertRequestDtoSchema(long id) {
      LOG.debug("Get EmployeeInsertRequestDtoSchema with id {} from database.", id);
      Optional<EmployeeInsertRequestDtoSchemaEntity> foundEntity = getEmployeeInsertRequestDtoSchemaRepository().findById(id);
      if (foundEntity.isPresent())
        return getBeanMapper().map(foundEntity.get(), EmployeeInsertRequestDtoSchemaEto.class);
      else
        return null;
    }

    @Override
    public Page<EmployeeInsertRequestDtoSchemaEto> findEmployeeInsertRequestDtoSchemas(EmployeeInsertRequestDtoSchemaSearchCriteriaTo criteria) {
      Page<EmployeeInsertRequestDtoSchemaEntity> employeeinsertrequestdtoschemas = getEmployeeInsertRequestDtoSchemaRepository().findByCriteria(criteria);
    return mapPaginatedEntityList(employeeinsertrequestdtoschemas, EmployeeInsertRequestDtoSchemaEto.class);
  }

}
