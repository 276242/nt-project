package com.example.ntproject.controller.book;

import com.example.ntproject.controller.book.dto.CreateBookDto;
import com.example.ntproject.controller.book.dto.CreateBookResponseDto;
import com.example.ntproject.controller.book.dto.GetBookDto;
import com.example.ntproject.service.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@PreAuthorize("hasRole('ADMIN')")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<GetBookDto> getAllBooks(){
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public GetBookDto getOneFromId(@PathVariable long id){
        return bookService.getOneFromId(id);
    }

    @GetMapping("/{isbn}")
    @PreAuthorize("isAuthenticated()")
    public GetBookDto getOneFromIsbn(@PathVariable String isbn){
        return bookService.getOneFromIsbn(isbn);
    }

    @PostMapping
    public ResponseEntity<CreateBookResponseDto> create(@RequestBody CreateBookDto book) {
        var newBook =  bookService.create(book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
