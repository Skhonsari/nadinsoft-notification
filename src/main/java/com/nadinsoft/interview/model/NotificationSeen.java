package com.nadinsoft.interview.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class NotificationSeen {

    @Id
    @GeneratedValue
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date seenDate;

    @ManyToOne
    private User user;

    @ManyToOne
    private Notification notification;
}
