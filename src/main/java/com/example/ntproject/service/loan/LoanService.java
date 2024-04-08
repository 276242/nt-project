package com.example.ntproject.service.loan;

import com.example.ntproject.controller.book.dto.GetBookDto;
import com.example.ntproject.controller.loan.dto.*;
import com.example.ntproject.controller.user.dto.GetUserDto;
import com.example.ntproject.infrastructure.entity.BookEntity;
import com.example.ntproject.infrastructure.entity.LoanEntity;
import com.example.ntproject.infrastructure.entity.UserEntity;
import com.example.ntproject.infrastructure.repository.AuthRepository;
import com.example.ntproject.infrastructure.repository.BookRepository;
import com.example.ntproject.infrastructure.repository.LoanRepository;
import com.example.ntproject.infrastructure.repository.UserRepository;
import com.example.ntproject.service.auth.OwnershipService;
import com.example.ntproject.service.book.error.BookNotFoundById;
import com.example.ntproject.service.loan.error.LoanNotFound;
import com.example.ntproject.service.user.error.UserNotFound;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;


@Service
public class LoanService extends OwnershipService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository, UserRepository userRepository, BookRepository bookRepository, AuthRepository authRepository) {
        super(authRepository);
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @PreAuthorize("hasRole('ADMIN') or isAuthenticated() and this.isOwner(authentication.name, #loanDto.userId)")
    public CreateLoanResponseDto create(CreateLoanDto loanDto) {

        UserEntity user = userRepository.findById(loanDto.getUserId()).orElseThrow(() -> UserNotFound.createById(loanDto.getUserId()));
        BookEntity book = bookRepository.findById(loanDto.getBookId()).orElseThrow(() -> BookNotFoundById.create(loanDto.getBookId()));

        LoanEntity loan = new LoanEntity();
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(new Date(System.currentTimeMillis()));
        loan.setDueDate(loanDto.getDueDate());
        loanRepository.save(loan);

        return new CreateLoanResponseDto(loan.getId(), loan.getLoanDate(), loan.getDueDate(), loan.getUser().getId(), loan.getBook().getId());
    }

    @PostAuthorize("hasRole('ADMIN') or isAuthenticated() and this.isOwner(authentication.name, returnObject.user.id)")
    public GetLoanResponseDto getOneById(long id) {
       LoanEntity loan = loanRepository.findById(id).orElseThrow(() -> LoanNotFound.create(id));
       return mapLoan(loan);
    }

    @PreAuthorize("hasRole('ADMIN') or isAuthenticated() and this.isOwner(authentication.name, #userId)")
    public GetLoansPageResponseDto getAll(Long userId, int page, int size ) {
        Page<LoanEntity> loansPage;

        Pageable pageable = PageRequest.of(page, size);

        if (userId == null) {
           loansPage = loanRepository.findAll(pageable);
        } else {
            loansPage = loanRepository.findByUserId(userId, pageable);
        }

        List<GetLoanResponseDto> loansDto = loansPage.stream().map(this::mapLoan).toList();

        return new GetLoansPageResponseDto(loansDto, loansPage.getNumber(), loansPage.getTotalElements(), loansPage.getTotalPages(), loansPage.hasNext());
    }

    private GetLoanResponseDto mapLoan(LoanEntity loan) {
        GetUserDto user = new GetUserDto(loan.getUser().getId(), loan.getUser().getName(), loan.getUser().getLastName(), loan.getUser().getEmail());
        GetBookDto book = new GetBookDto(
                loan.getBook().getId(),
                loan.getBook().getIsbn(),
                loan.getBook().getTitle(),
                loan.getBook().getAuthor(),
                loan.getBook().getPublisher(),
                loan.getBook().getPublicationYear(),
                loan.getBook().getAvailableCopies() > 0 );
        return new GetLoanResponseDto(loan.getId(), loan.getLoanDate(), loan.getDueDate(), user, book);
    }

    public void delete(long id) {
        if (!loanRepository.existsById(id)) {
            throw LoanNotFound.create(id);
        }
        loanRepository.deleteById(id);
    }

    public ReturnBookResponseDto returnBook(long id, Authentication authentication) {
        LoanEntity loan = loanRepository.findById(id).orElseThrow(() -> LoanNotFound.create(id));

        loan.setReturnDate(new Date(System.currentTimeMillis()));
        loanRepository.save(loan);

        return new ReturnBookResponseDto(loan.getId(),loan.getLoanDate(), loan.getDueDate(), loan.getUser().getId(), loan.getBook().getId(), loan.getReturnDate());
    }
}
