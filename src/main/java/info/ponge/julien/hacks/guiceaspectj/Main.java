package info.ponge.julien.hacks.guiceaspectj;

import com.google.inject.*;
import info.ponge.julien.hacks.guiceaspectj.aspects.ProfileVerification;
import info.ponge.julien.hacks.guiceaspectj.auth.UserProfileChecker;
import info.ponge.julien.hacks.guiceaspectj.auth.dumb.DumbUserProfileChecker;
import info.ponge.julien.hacks.guiceaspectj.contact.ContactManager;
import info.ponge.julien.hacks.guiceaspectj.contact.Person;
import info.ponge.julien.hacks.guiceaspectj.contact.simple.InMemoryContactManager;
import org.aspectj.lang.Aspects;

import static org.aspectj.lang.Aspects.*;

public class Main {

    public static void main(String[] args) {
        new Main().execute();
    }

    private Module guiceModule = new AbstractModule() {
        @Override
        protected void configure() {

            bind(ContactManager.class)
                    .to(InMemoryContactManager.class)
                    .in(Singleton.class);

            bind(UserProfileChecker.class)
                    .to(DumbUserProfileChecker.class)
                    .in(Singleton.class);

            requestInjection(aspectOf(ProfileVerification.class));

        }
    };

    private Injector injector = Guice.createInjector(guiceModule);

    private void execute() {
        ContactManager contacts = injector.getInstance(ContactManager.class);
        UserProfileChecker profileChecker = injector.getInstance(UserProfileChecker.class);

        profileChecker.login("Julien", "secret");

        contacts.add(new Person("Julien Ponge", "julien.ponge@gmail.com"));
        contacts.add(new Person("Jean-Jacques", "jean.jacques@gmail.com"));

        profileChecker.logout();
        profileChecker.login("Jean-Jacques", "1234");

        System.out.println(contacts.lookup("Julien Ponge"));

        profileChecker.logout();
        contacts.add(new Person("Mr Bean", "mrbean@gmail.com"));
    }

}
