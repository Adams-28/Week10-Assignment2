package net.skillspire.library_system;


import org.springframework.stereotype.Service;

// Creating a service class to test AOP methods
@Service
public class LibraryService {

    private final BookRepository bookRepository;


    public LibraryService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    // Creating service method
    public Book getBookById(Long id) {

        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book ID: " + id + " not found!"));


    }
}
