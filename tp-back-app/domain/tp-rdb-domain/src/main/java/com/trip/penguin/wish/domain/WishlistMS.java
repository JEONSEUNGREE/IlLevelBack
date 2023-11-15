package com.trip.penguin.wish.domain;

import com.trip.penguin.company.domain.CompanyMS;
import com.trip.penguin.user.domain.UserMS;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "WISHLIST_MS", schema = "tp-back-app")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WishlistMS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wish_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserMS userMS;

    @ManyToOne
    @JoinColumn(name = "com_id", nullable = false)
    private CompanyMS com;

}