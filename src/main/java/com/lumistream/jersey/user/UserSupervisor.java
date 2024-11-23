package com.lumistream.jersey.user;

import java.util.HashMap;
import java.util.Map;

public class UserSupervisor {
    private static UserSupervisor u = null;
    private static Map<String, Integer> log_users = null;
    private final static Integer APP1 = 1;
    private final static Integer APP2 = 2;
    private final static Integer BOTH = 2;

    private UserSupervisor() {
        u = new UserSupervisor();
        log_users = new HashMap<String, Integer>();
    }

    public static UserSupervisor getInstance() {
        if (log_users == null) {
            new UserSupervisor();
        }

        return u;
    }

    public static void loginUser(String name, String password, Integer app, String url) {
        if (User.Authenticate(name, password, url)) {
            if (UserSupervisor.isUserLoggedIn(name) == null) {
                log_users.put(name, app);
            } else if (UserSupervisor.isUserLoggedIn(name) != app) {
                log_users.replace(name, BOTH);
            }
        }
    }

    public static void logoutUser(String name, String password, Integer app) {

        if (UserSupervisor.isUserLoggedIn(name) == app) {
            log_users.remove(name);
        } else if (UserSupervisor.isUserLoggedIn(name) == BOTH) {
            if (app == APP1) {
                log_users.replace(name, APP2);
            } else if (app == APP2) {
                log_users.replace(name, APP1);
            }
        }

    }

    public static Integer isUserLoggedIn(String name) {
        return log_users.get(name);
    }

}
