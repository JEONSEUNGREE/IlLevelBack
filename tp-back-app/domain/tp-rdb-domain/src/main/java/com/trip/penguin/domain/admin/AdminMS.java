package com.trip.penguin.domain.admin;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ADMIN_MS", schema = "tp-back-app")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminMS {

    @Id
    @Column(name = "admin_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String adminId;

    @Column(name = "admin_email", nullable = false)
    private String adminEmail;

    @Column(name = "admin_pwd", nullable = false)
    private String adminPwd;

    @Column(name = "admin_nm", nullable = false)
    private String adminNm;
}