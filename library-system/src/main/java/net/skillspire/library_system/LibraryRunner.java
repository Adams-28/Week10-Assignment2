package net.skillspire.library_system;


import jakarta.persistence.EntityManager;
import org.springframework.aop.support.AopUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDate;

@Component
public class LibraryRunner implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final LibraryService libraryService;
    private final TransactionTemplate transactionTemplate;

    public LibraryRunner(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository, LibraryService libraryService, TransactionTemplate transactionTemplate) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.libraryService = libraryService;
        this.transactionTemplate = transactionTemplate;
    }




    @Override
    public void run(String... args) {

        transactionTemplate.executeWithoutResult(status -> {

            // Creating authors, books

            Author author1 = new Author("Timothy Zahn");
            Author author2 = new Author("Christie Golden");

            Book book1 = new Book("Star Wars: Heir to the Empire");
            Book book2 = new Book("Cobra Alliance");

            Book book3 = new Book("World of Warcraft: Before the Storm");
            Book book4 = new Book("Arthas: Rise of the Lich King");



            // Adding publishers to the books
            Publisher publisher1 = new Publisher("Bantam Spectra", LocalDate.of(1991, 5, 1));
            Publisher publisher2 = new Publisher("Baen Books", LocalDate.of(2009, 12, 1));
            Publisher publisher3 = new Publisher("Pocket Books", LocalDate.of(2009, 4, 21));
            Publisher publisher4 = new Publisher("Del Rey", LocalDate.of(2018, 6, 12));

            // Adding authors to the created books
            author1.addBook(book1);
            author1.addBook(book2);
            author2.addBook(book3);
            author2.addBook(book4);

            //Assigning the publishers to each book
            book1.assignPublisher(publisher1);
            book2.assignPublisher(publisher2);
            book3.assignPublisher(publisher3);
            book4.assignPublisher(publisher4);

            //Creating the genres
            Genre genre1 = new Genre("Science Fiction");
            Genre genre2 = new Genre("Epic Fantasy");
            Genre genre3 = new Genre("Adventure");
            Genre genre4 = new Genre("Space Opera");

            // Assigning and saving genres
            book1.addGenre(genre1);
            book1.addGenre(genre4);
            book2.addGenre(genre1);
            book2.addGenre(genre4);
            book3.addGenre(genre2);
            book3.addGenre(genre3);
            book4.addGenre(genre2);
            book4.addGenre(genre3);

            genreRepository.save(genre1);
            genreRepository.save(genre2);
            genreRepository.save(genre3);
            genreRepository.save(genre4);



            // Saving the authors and books

            authorRepository.save(author1);
            authorRepository.save(author2);

            bookRepository.save(book1);
            bookRepository.save(book2);
            bookRepository.save(book3);
            bookRepository.save(book4);

            // Testing the Library Service
            System.out.println("\n-- TEST --");

            System.out.println("LibraryService class: "
                    + libraryService.getClass().getName());

            System.out.println("Is LibraryService an AOP proxy? "
                    + AopUtils.isAopProxy(libraryService));

            Book foundBook = libraryService.getBookById(book1.getId());

            System.out.println("Book Retrieved: " + foundBook.getTitle());

            // Testing @AfterThrowing advice
            System.out.println("\n--- Logging Exception Test ---");

            try {
                libraryService.getBookById(586L);

            } catch (IllegalArgumentException exception) {

                System.out.println("Exception caught: " + exception.getMessage());
            }


            // Testing Validation Aspect with a negative number
            System.out.println("\n--- Validation Test ---");

            try {
                libraryService.getBookById(-4L);
            } catch (IllegalArgumentException exception) {

                System.out.println("(Validation) Invalid ID:  " + exception.getMessage());
            }



            // Testing Validation Aspect with a null value
            System.out.println("\n--- Null Validation Test ---");

            try {
                libraryService.getBookById(null);
            } catch (IllegalArgumentException exception) {

                System.out.println("(Validation) Null ID: " + exception.getMessage());
            }











            // Testing that the relationship between all entities is working

            System.out.println("\n--- Authors and Books ---");

            for(Author author: authorRepository.findAll()) {

                System.out.println("Author: " + author.getName());

                for (Book book : author.getBooks()) {
                    System.out.println(" Book: " +book.getTitle());

                    System.out.println(" Publisher: " + book.getPublisher().getPublisherName());

                    System.out.println(" Contract Date: " + book.getPublisher().getContractDate());

                    System.out.println(" Genres: ");

                    for (Genre genre : book.getGenres()) {
                        System.out.println("   -" + genre.getName());
                    }

                    System.out.println();
                }


            }




        });


    }
}
