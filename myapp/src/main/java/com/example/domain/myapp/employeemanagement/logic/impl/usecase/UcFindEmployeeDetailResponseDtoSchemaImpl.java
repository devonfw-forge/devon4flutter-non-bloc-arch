package com.example.domain.myapp.employeemanagement.logic.impl.usecase;

import com.example.domain.myapp.employeemanagement.logic.api.to.EmployeeDetailResponseDtoSchemaEto;
import com.example.domain.myapp.employeemanagement.logic.api.usecase.UcFindEmployeeDetailResponseDtoSchema;
import com.example.domain.myapp.employeemanagement.logic.base.usecase.AbstractEmployeeDetailResponseDtoSchemaUc;
import com.example.domain.myapp.employeemanagement.dataaccess.api.EmployeeDetailResponseDtoSchemaEntity;
import com.example.domain.myapp.employeemanagement.logic.api.to.EmployeeDetailResponseDtoSchemaSearchCriteriaTo;
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
 * Use case implementation for searching, filtering and getting EmployeeDetailResponseDtoSchemas
 */
@Named
@Validated
@Transactional
public class UcFindEmployeeDetailResponseDtoSchemaImpl extends AbstractEmployeeDetailResponseDtoSchemaUc implements UcFindEmployeeDetailResponseDtoSchema {

	  /** Logger instance. */
    private static final Logger LOG = LoggerFactory.getLogger(UcFindEmployeeDetailResponseDtoSchemaImpl.class);


    @Override
    public EmployeeDetailResponseDtoSchemaEto findEmployeeDetailResponseDtoSchema(long id) {
      LOG.debug("Get EmployeeDetailResponseDtoSchema with id {} from database.", id);
      Optional<EmployeeDetailResponseDtoSchemaEntity> foundEntity = getEmployeeDetailResponseDtoSchemaRepository().findById(id);
      if (foundEntity.isPresent())
        return getBeanMapper().map(foundEntity.get(), EmployeeDetailResponseDtoSchemaEto.class);
      else
        return null;
    }

    @Override
    public Page<EmployeeDetailResponseDtoSchemaEto> findEmployeeDetailResponseDtoSchemas(EmployeeDetailResponseDtoSchemaSearchCriteriaTo criteria) {
      Page<EmployeeDetailResponseDtoSchemaEntity> employeedetailresponsedtoschemas = getEmployeeDetailResponseDtoSchemaRepository().findByCriteria(criteria);
    return mapPaginatedEntityList(employeedetailresponsedtoschemas, EmployeeDetailResponseDtoSchemaEto.class);
  }

}
