package com.trip.penguin.domain.user;

import com.trip.penguin.domain.pay.PayStatusMS;
import com.trip.penguin.domain.room.RoomMS;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "BOOKING_MS", schema = "tp-back-app")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingMS {

    @Id
    @Column(name = "book_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "roomId", nullable = false)
    private RoomMS room;

    @Column(name = "book_nm", nullable = false)
    private String bookNm;

    @Column(name = "check_in", nullable = false)
    private LocalDate checkIn;

    @Column(name = "check_out", nullable = false)
    private LocalDate checkOut;

    @Column(name = "pay_method", nullable = false)
    private String payMethod;

    @Column(name = "sell_prc", nullable = false)
    private Integer sellPrc;

    @Column(name = "pay_amount", nullable = false)
    private Integer payAmount;

    @Column(name = "coupon_yn", nullable = false)
    private Integer couponYn;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(name = "modified_date", nullable = false)
    private LocalDate modifiedDate;

}