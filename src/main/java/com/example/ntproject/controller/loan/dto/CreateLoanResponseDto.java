package com.example.ntproject.controller.loan.dto;

import com.example.ntproject.controller.book.dto.GetBookDto;
import com.example.ntproject.controller.user.dto.GetUserDto;

import java.sql.Date;

public class CreateLoanResponseDto {
    private long id;
    private Date loanDate;
    private Date dueDate;
    private GetUserDto user;
    private GetBookDto book;

    public CreateLoanResponseDto(long id, Date loanDate, Date dueDate, GetUserDto user, GetBookDto book) {
        this.id = id;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.user = user;
        this.book = book;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public GetUserDto getUser() {
        return user;
    }

    public void setUser(GetUserDto user) {
        this.user = user;
    }

    public GetBookDto getBook() {
        return book;
    }

    public void setBook(GetBookDto book) {
        this.book = book;
    }
}


