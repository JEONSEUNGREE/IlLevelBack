package com.trip.penguin.room.domain;

import com.trip.penguin.booking.domain.BookingMS;
import com.trip.penguin.company.domain.CompanyMS;
import com.trip.penguin.pay.domain.PayStatusMS;
import com.trip.penguin.review.domain.ReviewMS;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ROOM_MS", schema = "tp-back-app")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomMS {

    @Id
    @Column(name = "room_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "roomMs", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomPicMS> roomPicList = new ArrayList<>();

    @OneToMany(mappedBy = "roomMS", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewMS> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookingMS> bookingList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "com_id", nullable = false)
    private CompanyMS com;

    @Column(name = "room_nm", nullable = false)
    private String roomNm;

    @Column(name = "com_name", nullable = false)
    private String comName;

    @Column(name = "sell_prc", nullable = false)
    private Integer sellPrc;

    @Column(name = "sold_out", nullable = false)
    private String soldOutYn;

    @Column(name = "check_in", nullable = false)
    private LocalDateTime checkIn;

    @Column(name = "check_out", nullable = false)
    private LocalDateTime checkOut;

    @Column(name = "room_desc")
    private String roomDesc;

    @Column(name = "coupon_yn", nullable = false, length = 2)
    private String couponYn;

    @Column(name = "max_count", nullable = false)
    private Integer maxCount;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "modified_date", nullable = false)
    private LocalDateTime modifiedDate;

    public void setCompanyInfo(CompanyMS companyInfo) {
        this.com = companyInfo;
        this.comName = companyInfo.getCom_nm();
    }
}