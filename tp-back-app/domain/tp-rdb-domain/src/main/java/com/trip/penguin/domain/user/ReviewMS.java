package com.trip.penguin.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "REVIEW_MS", schema = "tp-back-app")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewMS {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserMS userMS;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private BookingMS bookingMS;

    @Column(name = "re_title", nullable = false, length = 100)
    private String reTitle;

    @Column(name = "re_content", nullable = false, length = 1000)
    private String reContent;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "re_accom", length = 1000)
    private String reAccom;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "modified_date", nullable = false)
    private LocalDate modifiedDate;

    @Column(name = "report", nullable = false)
    private Integer report;

}