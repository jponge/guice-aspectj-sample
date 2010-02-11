package info.ponge.julien.hacks.guiceaspectj.contact.simple;

import info.ponge.julien.hacks.guiceaspectj.auth.RequiresProfile;
import info.ponge.julien.hacks.guiceaspectj.auth.WithUserProfileVerification;
import info.ponge.julien.hacks.guiceaspectj.contact.ContactManager;
import info.ponge.julien.hacks.guiceaspectj.contact.Person;

import java.util.HashSet;
import java.util.Set;

import static info.ponge.julien.hacks.guiceaspectj.auth.UserProfile.ADMIN;
import static info.ponge.julien.hacks.guiceaspectj.auth.UserProfile.USER;

@WithUserProfileVerification
public class InMemoryContactManager implements ContactManager {

    private final Set<Person> contacts = new HashSet<Person>();

    @RequiresProfile(ADMIN)
    public ContactManager add(Person person) {
        contacts.add(person);
        return this;
    }

    @RequiresProfile(ADMIN)
    public ContactManager remove(Person person) {
        contacts.remove(person);
        return this;
    }

    @RequiresProfile(USER)
    public Person lookup(String name) {
        for (Person person : contacts) {
            if (person.getName().equals(name)) {
                return person;
            }
        }
        return null;
    }

}
