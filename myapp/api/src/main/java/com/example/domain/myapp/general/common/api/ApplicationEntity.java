package com.example.domain.myapp.general.common.api;

import com.devonfw.module.basic.common.api.entity.GenericEntity;

/**
 * This is the abstract interface for a {@link GenericEntity} of this application. We are using {@link Long} for
 * all {@link #getId() primary keys}.
 */
public abstract interface ApplicationEntity extends GenericEntity<Long> {

}
