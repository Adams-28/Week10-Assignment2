package net.skillspire.library_system;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidationAspect {

    @Pointcut("execution(* net.skillspire.library_system.LibraryService.getBookById(..)) && args(id)")
    public void bookIdMethods(Long id) {

    }

    // Creating a before method to validate a book ID
    @Before(value = "bookIdMethods(id)", argNames = "id")
    public void validateBookId(Long id) {

        if (id == null || id <= 0) {

            throw new IllegalArgumentException("Invalid Book ID: ID must have a number and be greater than zero!");
        }

        System.out.println("(Validation) Book ID: " + id + " is valid!");
    }


}
