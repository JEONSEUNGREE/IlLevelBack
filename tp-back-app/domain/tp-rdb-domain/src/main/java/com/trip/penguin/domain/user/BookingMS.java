//package com.trip.penguin.domain.user;
//
//import jakarta.persistence.*;
//
//import java.time.LocalDate;
//import java.util.LinkedHashSet;
//import java.util.Set;
//
//@Entity
//@Table(name = "BOOKING_MS", schema = "tp-back-app")
//public class BookingMS {
//    @Id
//    @Column(name = "book_id", nullable = false)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "roomId", nullable = false)
//    private RoomMS room;
//
//    @Column(name = "book_nm", nullable = false)
//    private String bookNm;
//
//    @Column(name = "check_in", nullable = false)
//    private LocalDate checkIn;
//
//    @Column(name = "check_out", nullable = false)
//    private LocalDate checkOut;
//
//    @Column(name = "pay_method", nullable = false)
//    private String payMethod;
//
//    @Column(name = "sell_prc", nullable = false)
//    private Integer sellPrc;
//
//    @Column(name = "pay_amount", nullable = false)
//    private Integer payAmount;
//
//    @Column(name = "coupon_yn", nullable = false)
//    private Integer couponYn;
//
//    @Column(name = "create_date", nullable = false)
//    private LocalDate createDate;
//
//    @Column(name = "modified_date", nullable = false)
//    private LocalDate modifiedDate;
//
//    @OneToOne(mappedBy = "bookingM")
//    private PayStatusMS payStatusMS;
//
//    @OneToMany(mappedBy = "bookId2")
//    private Set<ReviewMS> reviewMs = new LinkedHashSet<>();
//
//    public Long getId() {
//        return id;
//    }
////
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public RoomMS getRoom() {
//        return room;
//    }
//
//    public void setRoom(RoomMS room) {
//        this.room = room;
//    }
//
//    public String getBookNm() {
//        return bookNm;
//    }
//
//    public void setBookNm(String bookNm) {
//        this.bookNm = bookNm;
//    }
//
//    public LocalDate getCheckIn() {
//        return checkIn;
//    }
//
//    public void setCheckIn(LocalDate checkIn) {
//        this.checkIn = checkIn;
//    }
//
//    public LocalDate getCheckOut() {
//        return checkOut;
//    }
//
//    public void setCheckOut(LocalDate checkOut) {
//        this.checkOut = checkOut;
//    }
//
//    public String getPayMethod() {
//        return payMethod;
//    }
//
//    public void setPayMethod(String payMethod) {
//        this.payMethod = payMethod;
//    }
//
//    public Integer getSellPrc() {
//        return sellPrc;
//    }
//
//    public void setSellPrc(Integer sellPrc) {
//        this.sellPrc = sellPrc;
//    }
//
//    public Integer getPayAmount() {
//        return payAmount;
//    }
//
//    public void setPayAmount(Integer payAmount) {
//        this.payAmount = payAmount;
//    }
//
//    public Integer getCouponYn() {
//        return couponYn;
//    }
//
//    public void setCouponYn(Integer couponYn) {
//        this.couponYn = couponYn;
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
//    public PayStatusMS getPayStatusM() {
//        return payStatusMS;
//    }
//
//    public void setPayStatusM(PayStatusMS payStatusMS) {
//        this.payStatusMS = payStatusMS;
//    }
//
//    public Set<ReviewMS> getReviewMs() {
//        return reviewMs;
//    }
//
//    public void setReviewMs(Set<ReviewMS> reviewMs) {
//        this.reviewMs = reviewMs;
//    }
//
//}