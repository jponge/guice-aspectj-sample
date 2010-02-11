package info.ponge.julien.hacks.guiceaspectj.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class ContactManagerLogger {

    @Before("call( * info.ponge.julien.hacks.guiceaspectj.contact.ContactManager.*(..) )")
    public void methodCalled(JoinPoint thisJoinPoint) {
        System.out.println("Calling: " + thisJoinPoint.getSignature().getName());
    }

}
