package com.example.ntproject.controller.loan.dto;

import java.sql.Date;

public class ReturnBookResponseDto {
    private long id;
    private Date loanDate;
    private Date dueDate;
    private long userId;
    private long bookId;
    private Date returnDate;

    public ReturnBookResponseDto(long id, Date loanDate, Date dueDate, long userId, long bookId, Date returnDate) {
        this.id = id;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.userId = userId;
        this.bookId = bookId;
        this.returnDate = returnDate;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
