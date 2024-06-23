package com.example.ntproject.controller.loan;

import com.example.ntproject.controller.loan.dto.*;
import com.example.ntproject.service.loan.LoanService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@PostAuthorize("hasRole('ADMIN')")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<CreateLoanResponseDto> create(@RequestBody @Validated CreateLoanDto loan) {
        var newLoan = loanService.create(loan);
        return new ResponseEntity<>(newLoan, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetLoanResponseDto> getOneById(@PathVariable long id) {
        GetLoanResponseDto dto = loanService.getOneById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GetLoanResponseDto>> getLoanByUserId(@PathVariable long userId) {
        List<GetLoanResponseDto> dto = loanService.getLoansByUserId(userId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    public ResponseEntity<GetLoansPageResponseDto> getAll(@RequestParam(required = false) Long userId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){

        GetLoansPageResponseDto dto = loanService.getAll(userId, page, size);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        loanService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/return/{id}")
    public ResponseEntity<ReturnBookResponseDto> returnBook(@PathVariable long id, Authentication authentication) {
        return new ResponseEntity<>(loanService.returnBook(id, authentication), HttpStatus.OK);
    }
}
