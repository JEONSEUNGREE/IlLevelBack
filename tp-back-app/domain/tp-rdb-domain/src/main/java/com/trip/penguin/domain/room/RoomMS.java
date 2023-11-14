//package com.trip.penguin.domain.room;
//
////import com.trip.penguin.domain.user.BookingMS;
////import com.trip.penguin.domain.company.CompanyMS;
//import jakarta.persistence.*;
//
//import java.time.LocalDate;
//import java.util.LinkedHashSet;
//import java.util.Set;
//
//@Entity
//@Table(name = "ROOM_MS", schema = "tp-back-app")
//public class RoomMS {
//    @Id
//    @Column(name = "roomId", nullable = false)
//    private Long id;
//
////    @ManyToOne(fetch = FetchType.LAZY, optional = false)
////    @JoinColumn(name = "com_id", nullable = false)
////    private CompanyMS com;
//
//    @Column(name = "room_nm", nullable = false)
//    private String roomNm;
//
//    @Column(name = "com_name", nullable = false)
//    private String comName;
//
//    @Column(name = "sell_prc", nullable = false)
//    private Integer sellPrc;
//
//    @Column(name = "check_in", nullable = false)
//    private LocalDate checkIn;
//
//    @Column(name = "check_out", nullable = false)
//    private LocalDate checkOut;
//
//    @Column(name = "room_desc")
//    private String roomDesc;
//
//    @Column(name = "coupon_yn", nullable = false, length = 2)
//    private String couponYn;
//
//    @Column(name = "max_count", nullable = false)
//    private Integer maxCount;
//
//    @Column(name = "created_date", nullable = false)
//    private LocalDate createdDate;
//
//    @Column(name = "modified_date", nullable = false)
//    private LocalDate modifiedDate;
//}