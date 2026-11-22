package net.skillspire.library_system;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "publishers")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String publisherName;
    private LocalDate contractDate;

    public Publisher() {


    }

    public Publisher(String publisherName, LocalDate contractDate) {
        this.publisherName = publisherName;
        this.contractDate = contractDate;
    }

   public Long getId() {
        return id;
   }

   public void setId(Long id) {
        this.id = id;
   }


    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public LocalDate getContractDate() {
        return contractDate;
    }

    public void setContractDate(LocalDate contractDate) {
        this.contractDate = contractDate;
    }

    public void setBook(Book book) {
        this.book = book;
    }


    @OneToOne(mappedBy = "publisher", fetch = FetchType.LAZY)
    private Book book;




}
