package info.ponge.julien.hacks.guiceaspectj.auth.dumb;

import info.ponge.julien.hacks.guiceaspectj.auth.UserProfile;
import info.ponge.julien.hacks.guiceaspectj.auth.UserProfileChecker;

import static info.ponge.julien.hacks.guiceaspectj.auth.UserProfile.*;

public class DumbUserProfileChecker implements UserProfileChecker {

    private UserProfile userProfile = ANONYMOUS;

    public UserProfile getCurrentUserProfile() {
        return userProfile;
    }

    public UserProfile login(String login, String password) {
        if (login.equals("Julien") && password.equals("secret")) {
            userProfile = ADMIN;
        } else if (login.equals("Jean-Jacques") && password.equals("1234")) {
            userProfile = USER;
        } else {
            userProfile = ANONYMOUS;
        }

        return getCurrentUserProfile();
    }

    public UserProfile logout() {
        userProfile = ANONYMOUS;
        return userProfile;
    }

}
