package com.trip.penguin.domain.admin;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "CS_MS", schema = "tp-back-app")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CsMS {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
//    @JoinColumn(name = "user_id", nullable = false)


    @Column(name = "csq_title", nullable = false, length = 100)
    private String csqTitle;

    @Column(name = "csq_content", nullable = false, length = 1000)
    private String csqContent;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "csq_status", nullable = false)
    private Integer csqStatus;

    @Column(name = "csa_title", length = 100)
    private String csaTitle;

    @Column(name = "csa_content", length = 1000)
    private String csaContent;

    @Column(name = "admin_nm", nullable = false)
    private String adminNm;

}