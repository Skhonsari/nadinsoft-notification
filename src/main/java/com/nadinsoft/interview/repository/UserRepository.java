package com.nadinsoft.interview.repository;

import com.nadinsoft.interview.model.Role;
import com.nadinsoft.interview.model.User;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepositoryImplementation<User, Long> {

    User findByUsername(String username);
    Set<User> findAllByUsernameIn(List<String> username);

    Set<User> findAllByRolesContains(Role role);
}
