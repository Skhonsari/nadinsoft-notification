package com.nadinsoft.interview.repository;

import com.nadinsoft.interview.model.Notification;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepositoryImplementation<Notification, Long> {
}
