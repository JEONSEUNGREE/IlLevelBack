package com.trip.penguin.company.domain;

import com.trip.penguin.room.domain.RoomMS;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "COMPANY_MS", schema = "tp-back-app")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyMS {

    @Id
    @Column(name = "com_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "com", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RoomMS> companyList = new ArrayList<>();

    @Column(name = "com_nm", nullable = false)
    private String com_nm;

    @Column(name = "com_email", nullable = false)
    private String comEmail;

    @Column(name = "com_pwd", nullable = false)
    private String comPwd;

    @Column(name = "com_img", nullable = false)
    private String comImg;

    @Column(name = "com_address", nullable = false)
    private String comAddress;

    @Column(name = "com_approval", nullable = false)
    private Integer comApproval;

    @Column(name = "user_role", nullable = false)
    private String userRole;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "modified_date", nullable = false)
    private LocalDateTime modifiedDate;

}