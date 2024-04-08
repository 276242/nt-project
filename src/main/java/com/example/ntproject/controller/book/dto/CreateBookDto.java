package com.example.ntproject.controller.book.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CreateBookDto {

    @NotBlank(message = "ISBN is mandatory")
    private String isbn;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Author is mandatory")
    private String author;

    private String publisher;
    private int publicationYear;

    @Min(value = 1, message = "At least one copy should be available")
    private int availableCopies;

    public CreateBookDto(String isbn, String title, String author, String publisher, int publicationYear, int availableCopies) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.availableCopies = availableCopies;
    }

    public CreateBookDto() {
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }
}
