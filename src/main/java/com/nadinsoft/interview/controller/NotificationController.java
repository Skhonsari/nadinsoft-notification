package com.nadinsoft.interview.controller;

import com.nadinsoft.interview.dto.NotificationCreationDTO;
import com.nadinsoft.interview.dto.NotificationDTO;
import com.nadinsoft.interview.dto.NotificationSeenDTO;
import com.nadinsoft.interview.model.Notification;
import com.nadinsoft.interview.model.NotificationSeen;
import com.nadinsoft.interview.model.Role;
import com.nadinsoft.interview.model.User;
import com.nadinsoft.interview.repository.NotificationRepository;
import com.nadinsoft.interview.repository.NotificationSeenRepository;
import com.nadinsoft.interview.repository.RoleRepository;
import com.nadinsoft.interview.repository.UserRepository;
import com.nadinsoft.interview.utils.Security;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationRepository notificationRepository;
    private final NotificationSeenRepository notificationSeenRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @GetMapping("/me")
    public List<NotificationSeenDTO> getNotificationByUser() {
        final User currentUser = Security.getCurrentUser();
        if (currentUser == null) {
            return new ArrayList<>();
        }
        return notificationSeenRepository.findAllByUserUsername(currentUser.getUsername()).stream()
                .map(NotificationSeenDTO.SeenPerNotification::load)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<NotificationDTO> getAllNotifications() {
        if (!Security.isCurrentUserAdmin()) {
            return new ArrayList<>();
        }
        return notificationRepository.findAll().stream()
                .map(notification -> NotificationDTO.load(notification, true))
                .collect(Collectors.toList());
    }

    @GetMapping("/user/{username}")
    public List<NotificationSeenDTO> getNotificationByUser(@PathVariable String username) {
        if (!Security.isCurrentUserAdmin()) {
            return new ArrayList<>();
        }
        return notificationSeenRepository.findAllByUserUsername(username).stream()
                .map(NotificationSeenDTO.SeenPerNotification::load)
                .collect(Collectors.toList());
    }

    @GetMapping("/{notificationId}")
    public NotificationDTO getNotificationById(@PathVariable Long notificationId) {
        return NotificationDTO.load(notificationRepository.findById(notificationId).orElseThrow(() -> new IllegalArgumentException("notification no found")), true);
    }

    @PostMapping("/seen/{notificationId}")
    public void seen(@PathVariable Long notificationId) {
        final User currentUser = Security.getCurrentUser();
        if (currentUser == null) {
            return;
        }
        final NotificationSeen notificationSeen = notificationSeenRepository.findByNotificationIdAndUserUsername(notificationId, currentUser.getUsername());
        if (notificationSeen.getSeenDate() == null) {
            notificationSeen.setSeenDate(new Date());
            notificationSeenRepository.save(notificationSeen);
        }
    }

    @PostMapping("/role/{roleId}")
    public NotificationDTO sendToRole(@RequestBody NotificationCreationDTO notificationDTO, @PathVariable Long roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new IllegalArgumentException("role with id " + roleId + " not found"));

        return createNotificationSeens(notificationDTO.getMessage(), role.getUsers());
    }

    @PostMapping("/user/{username}")
    public NotificationDTO sendToUser(@RequestBody NotificationCreationDTO notificationDTO, @PathVariable String username) {
        final User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("user with username " + username + " not found");
        }
        final Set<User> users = Collections.singleton(user);

        return createNotificationSeens(notificationDTO.getMessage(), users);
    }

    @DeleteMapping("/{notificationId}")
    public void deleteNotification(@PathVariable Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    @DeleteMapping("/{notificationId}/{username}")
    @Transactional
    public void deleteNotificationForUser(@PathVariable Long notificationId, @PathVariable String username) {
        notificationSeenRepository.deleteByNotificationIdAndUserUsername(notificationId, username);
    }


    private NotificationDTO createNotificationSeens(String message, Set<User> users) {
        Notification notification = notificationRepository.save(new Notification(message));

        final Notification finalNotification = notification;
        Set<NotificationSeen> notificationSeens = users.stream().map(user -> {
            final NotificationSeen notificationSeen = new NotificationSeen();
            notificationSeen.setNotification(finalNotification);
            notificationSeen.setUser(user);
            return notificationSeen;
        }).collect(Collectors.toSet());

        notification.getNotificationSeens().addAll(notificationSeens);
        notification = notificationRepository.save(notification);

        return NotificationDTO.load(notification, true);
    }
}
