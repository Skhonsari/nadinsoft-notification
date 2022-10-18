package com.nadinsoft.interview.dto;

import com.nadinsoft.interview.model.NotificationSeen;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NotificationSeenDTO {

    private Date seenDate;


    @Getter
    @Setter
    public static class SeenPerUser extends NotificationSeenDTO {

        private UserDTO user;

        public static SeenPerUser load(NotificationSeen notificationSeen) {
            final SeenPerUser dto = new SeenPerUser();
            dto.setUser(UserDTO.load(notificationSeen.getUser()));
            dto.setSeenDate(notificationSeen.getSeenDate());

            return dto;
        }
    }


    @Getter
    @Setter
    public static class SeenPerNotification extends NotificationSeenDTO {

        private NotificationDTO notification;

        public static SeenPerNotification load(NotificationSeen notificationSeen) {
            final SeenPerNotification dto = new SeenPerNotification();
            dto.setNotification(NotificationDTO.load(notificationSeen.getNotification(), false));
            dto.setSeenDate(notificationSeen.getSeenDate());

            return dto;
        }
    }
}
