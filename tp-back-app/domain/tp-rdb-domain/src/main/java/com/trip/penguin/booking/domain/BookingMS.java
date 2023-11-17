package com.trip.penguin.booking.domain;

import com.trip.penguin.pay.domain.PayStatusMS;
import com.trip.penguin.room.domain.RoomMS;
import com.trip.penguin.room.domain.RoomPicMS;
import com.trip.penguin.user.domain.UserMS;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "bookingMs", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PayStatusMS> payStatusList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private RoomMS room;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserMS userMS;

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
    private LocalDateTime createDate;

    @Column(name = "modified_date", nullable = false)
    private LocalDateTime modifiedDate;

}