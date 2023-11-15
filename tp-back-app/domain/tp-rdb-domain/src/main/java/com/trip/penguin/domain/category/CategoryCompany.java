//package com.trip.penguin.domain.category;
//
//import com.trip.penguin.domain.company.CompanyMS;
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "CATEGORY_COMPANY", schema = "tp-back-app")
//public class CategoryCompany {
//
//    @Id
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "com_id", nullable = false)
//    private CompanyMS com;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "cate_id", nullable = false)
//    private CategoryMS cate;
//
//}