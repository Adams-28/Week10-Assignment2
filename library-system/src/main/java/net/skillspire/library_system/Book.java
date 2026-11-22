package net.skillspire.library_system;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String title;


    public Book() {

    }

    public Book(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void setAuthor(Author author) {
        this.author = author;
    }


    public Publisher getPublisher() {
        return publisher;
    }


    public Set<Genre> getGenres() {
        return genres;
    }




    // Adding publisher method
    public void assignPublisher(Publisher publisher) {
        if (this.publisher != null && this.publisher != publisher) {
            this.publisher.setBook(null);
        }

        this.publisher = publisher;

        if (publisher != null) {
            publisher.setBook(this);
        }
    }

    // Adding genre method
    public void addGenre(Genre genre) {
        genres.add(genre);
        genre.getBooks().add(this);
    }


    // Modifying toString so that the result of the logAfterReturning method is legible
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }



    // Setting up Author relationship
    // The Lazy fetch type was se
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;


    // Setting up Publisher relationship
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "publisher_id", nullable = false, unique = true)
    private Publisher publisher;


    // Setting up genre relationship
    @ManyToMany
    @JoinTable(
            name = "book_genres",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();


}
