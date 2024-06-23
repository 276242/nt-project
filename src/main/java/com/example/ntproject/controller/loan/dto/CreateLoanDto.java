package com.example.ntproject.controller.loan.dto;

import jakarta.validation.constraints.NotNull;

import java.sql.Date;

public class CreateLoanDto {

    @NotNull
    private Date dueDate;
    @NotNull
    private Date loanDate;
    @NotNull
    private Long userId;
    @NotNull
    private Long bookId;

    public CreateLoanDto() {
    }

    public CreateLoanDto(Date dueDate, Date loanDate, Long userId, Long bookId) {
        this.dueDate = dueDate;
        this.loanDate = loanDate;
        this.userId = userId;
        this.bookId = bookId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }
}
