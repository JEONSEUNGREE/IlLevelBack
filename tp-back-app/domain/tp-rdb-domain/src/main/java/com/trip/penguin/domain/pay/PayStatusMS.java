package com.trip.penguin.domain.pay;

import com.trip.penguin.domain.user.BookingMS;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "PAY_STATUS_MS", schema = "tp-back-app")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayStatusMS {

    @Id
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private BookingMS bookingMs;

    @Column(name = "book_status", nullable = false)
    private Integer bookStatus;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "modified_date", nullable = false)
    private LocalDate modifiedDate;

}