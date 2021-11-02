package com.example.domain.myapp.employeemanagement.logic.impl.usecase;

import com.example.domain.myapp.employeemanagement.logic.api.to.EmployeeInsertResponseDtoSchemaEto;
import com.example.domain.myapp.employeemanagement.logic.api.usecase.UcFindEmployeeInsertResponseDtoSchema;
import com.example.domain.myapp.employeemanagement.logic.base.usecase.AbstractEmployeeInsertResponseDtoSchemaUc;
import com.example.domain.myapp.employeemanagement.dataaccess.api.EmployeeInsertResponseDtoSchemaEntity;
import com.example.domain.myapp.employeemanagement.logic.api.to.EmployeeInsertResponseDtoSchemaSearchCriteriaTo;
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
 * Use case implementation for searching, filtering and getting EmployeeInsertResponseDtoSchemas
 */
@Named
@Validated
@Transactional
public class UcFindEmployeeInsertResponseDtoSchemaImpl extends AbstractEmployeeInsertResponseDtoSchemaUc implements UcFindEmployeeInsertResponseDtoSchema {

	  /** Logger instance. */
    private static final Logger LOG = LoggerFactory.getLogger(UcFindEmployeeInsertResponseDtoSchemaImpl.class);


    @Override
    public EmployeeInsertResponseDtoSchemaEto findEmployeeInsertResponseDtoSchema(long id) {
      LOG.debug("Get EmployeeInsertResponseDtoSchema with id {} from database.", id);
      Optional<EmployeeInsertResponseDtoSchemaEntity> foundEntity = getEmployeeInsertResponseDtoSchemaRepository().findById(id);
      if (foundEntity.isPresent())
        return getBeanMapper().map(foundEntity.get(), EmployeeInsertResponseDtoSchemaEto.class);
      else
        return null;
    }

    @Override
    public Page<EmployeeInsertResponseDtoSchemaEto> findEmployeeInsertResponseDtoSchemas(EmployeeInsertResponseDtoSchemaSearchCriteriaTo criteria) {
      Page<EmployeeInsertResponseDtoSchemaEntity> employeeinsertresponsedtoschemas = getEmployeeInsertResponseDtoSchemaRepository().findByCriteria(criteria);
    return mapPaginatedEntityList(employeeinsertresponsedtoschemas, EmployeeInsertResponseDtoSchemaEto.class);
  }

}
