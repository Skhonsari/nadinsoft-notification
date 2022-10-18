package com.nadinsoft.interview.utils;

import com.nadinsoft.interview.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class Security {

    public static boolean isCurrentUserAdmin() {
        final User currentUser = getCurrentUser();
        return currentUser != null && currentUser.getUsername().equals("admin");
    }

    public static User getCurrentUser() {
        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        }
        return null;
    }
}
