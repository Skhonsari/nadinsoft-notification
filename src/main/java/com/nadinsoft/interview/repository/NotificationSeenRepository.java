package com.nadinsoft.interview.repository;

import com.nadinsoft.interview.model.NotificationSeen;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationSeenRepository extends JpaRepositoryImplementation<NotificationSeen, Long> {

    List<NotificationSeen> findAllByUserUsername(String username);
    NotificationSeen findByNotificationIdAndUserUsername(Long id, String username);

    void deleteByNotificationIdAndUserUsername(Long id, String username);
}
