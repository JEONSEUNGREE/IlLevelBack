package com.trip.penguin.domain.alert;

import com.trip.penguin.domain.user.UserMS;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "ALERT_MS", schema = "tp-back-app")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlertMS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alert_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserMS userMs;

    @Column(name = "alert_title", nullable = false)
    private String alertTitle;

    @Column(name = "alert_contents", nullable = false)
    private String alertContents;

    @Column(name = "alert_read", nullable = false)
    private Integer alertRead;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

}