package com.example.ntproject.service.book;


import com.example.ntproject.controller.book.dto.CreateBookDto;
import com.example.ntproject.controller.book.dto.CreateBookResponseDto;
import com.example.ntproject.controller.book.dto.GetBookDto;
import com.example.ntproject.infrastructure.entity.BookEntity;
import com.example.ntproject.infrastructure.repository.BookRepository;
import com.example.ntproject.service.book.error.BookAlreadyExists;
import com.example.ntproject.service.book.error.BookNotFoundById;
import com.example.ntproject.service.book.error.BookNotFoundByIsbn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PreAuthorize("isAuthenticated()")
    public List<GetBookDto> getAll() {
        var books = bookRepository.findAll();
        return books.stream().map(this::mapBook).collect(Collectors.toList());
    }

    public GetBookDto getOneFromId(Long id) {
        var book = bookRepository.findById(id).orElseThrow(() -> BookNotFoundById.create(id));
        return mapBook(book);
    }

    public GetBookDto getOneFromIsbn(String isbn) {
        var book = bookRepository.findByIsbn(isbn).orElseThrow(() -> BookNotFoundByIsbn.create((isbn)));
        return mapBook(book);
    }

    private GetBookDto mapBook(BookEntity book) {
        return new GetBookDto(
                book.getId(),
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublisher(),
                book.getPublicationYear(),
                book.getAvailableCopies() > 0
        );
    }

    public CreateBookResponseDto create(CreateBookDto book) {
        Optional<BookEntity> existingBook = bookRepository.findByIsbn(book.getIsbn());

        if (existingBook.isPresent()){
            throw BookAlreadyExists.create(Long.parseLong(book.getIsbn()));
        }


        var bookEntity = new BookEntity();

        bookEntity.setIsbn(book.getIsbn());
        bookEntity.setTitle(book.getTitle());
        bookEntity.setAuthor(book.getAuthor());
        bookEntity.setPublisher(book.getPublisher());
        bookEntity.setPublicationYear(book.getPublicationYear());
        bookEntity.setAvailableCopies(book.getAvailableCopies());

        var newBook = bookRepository.save(bookEntity);

        return new CreateBookResponseDto(
                newBook.getId(),
                newBook.getIsbn(),
                newBook.getTitle(),
                newBook.getAuthor(),
                newBook.getPublisher(),
                newBook.getPublicationYear(),
                newBook.getAvailableCopies()
        );
    }

    public void delete(long id) {
        if (!bookRepository.existsById(id)) {
            throw BookAlreadyExists.create(id);
        }
        bookRepository.deleteById(id);
    }
}

