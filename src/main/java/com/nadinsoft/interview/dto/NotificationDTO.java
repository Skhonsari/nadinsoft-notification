package com.nadinsoft.interview.dto;

import com.nadinsoft.interview.model.Notification;
import com.nadinsoft.interview.model.NotificationSeen;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {

    private Long id;

    private String message;

    private List<NotificationSeenDTO.SeenPerUser> users;

    public static NotificationDTO load(Notification notification, boolean loadSeens) {
        final NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setMessage(notification.getMessage());
        if (loadSeens) {
            dto.setUsers(notification.getNotificationSeens().stream().map(NotificationSeenDTO.SeenPerUser::load).collect(Collectors.toList()));
        }

        return dto;
    }

}
