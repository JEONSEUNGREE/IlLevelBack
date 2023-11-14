//package com.trip.penguin.domain.pay;
//
//import com.trip.penguin.domain.user.BookingMS;
//import jakarta.persistence.*;
//
//import java.time.LocalDate;
//
//@Entity
//@Table(name = "PAY_STATUS_MS", schema = "tp-back-app")
//public class PayStatusMS {
//    @Id
//    @Column(name = "book_id2", nullable = false)
//    private Long id;
//
//    @MapsId
//    @OneToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "book_id2", nullable = false)
//    private BookingMS bookingMs;
//
//    @Column(name = "book_status", nullable = false)
//    private Integer bookStatus;
//
//    @Column(name = "create_date")
//    private LocalDate createDate;
//
//    @Column(name = "modified_date", nullable = false)
//    private LocalDate modifiedDate;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public BookingMS getBookingMs() {
//        return bookingMs;
//    }
//
//    public void setBookingMs(BookingMS bookingMs) {
//        this.bookingMs = bookingMs;
//    }
//
//    public Integer getBookStatus() {
//        return bookStatus;
//    }
//
//    public void setBookStatus(Integer bookStatus) {
//        this.bookStatus = bookStatus;
//    }
//
//    public LocalDate getCreateDate() {
//        return createDate;
//    }
//
//    public void setCreateDate(LocalDate createDate) {
//        this.createDate = createDate;
//    }
//
//    public LocalDate getModifiedDate() {
//        return modifiedDate;
//    }
//
//    public void setModifiedDate(LocalDate modifiedDate) {
//        this.modifiedDate = modifiedDate;
//    }
//
//}