package info.ponge.julien.hacks.guiceaspectj.aspects;

import com.google.inject.Inject;
import info.ponge.julien.hacks.guiceaspectj.auth.RequiresProfile;
import info.ponge.julien.hacks.guiceaspectj.auth.UserProfile;
import info.ponge.julien.hacks.guiceaspectj.auth.UserProfileChecker;
import info.ponge.julien.hacks.guiceaspectj.auth.WithUserProfileVerification;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import static info.ponge.julien.hacks.guiceaspectj.auth.UserProfile.ADMIN;
import static info.ponge.julien.hacks.guiceaspectj.auth.UserProfile.USER;

@Aspect
public class ProfileVerification {

    @Inject
    UserProfileChecker userProfileChecker;

    @Before("execution( * *(..) ) && @annotation( required ) && within( @WithUserProfileVerification * )")
    public void verify(RequiresProfile required) {
        UserProfile expected = required.value();
        UserProfile current = userProfileChecker.getCurrentUserProfile();

        if (insufficientProfile(expected, current)) {
            throw new RuntimeException("The current user profile (" + current + ") is not sufficient: " + required);
        }
    }

    private boolean insufficientProfile(UserProfile required, UserProfile current) {
        return (required == ADMIN && current != ADMIN)
                || (required == USER && (current != USER && current != ADMIN));
    }

}
