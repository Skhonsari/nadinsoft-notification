package com.nadinsoft.interview.repository;

import com.nadinsoft.interview.model.Role;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface RoleRepository extends JpaRepositoryImplementation<Role, Long> {
}
