package info.ponge.julien.hacks.guiceaspectj.auth;

public interface UserProfileChecker {

    public UserProfile getCurrentUserProfile();

    public UserProfile login(String login, String password);

    public UserProfile logout();

}
