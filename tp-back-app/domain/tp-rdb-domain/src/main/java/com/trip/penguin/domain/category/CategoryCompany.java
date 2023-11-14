//package com.trip.penguin.domain.category;
//
//import com.trip.penguin.domain.company.CompanyMS;
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "CATEGORY_COMPANY", schema = "tp-back-app")
//public class CategoryCompany {
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "com_id", nullable = false)
//    private CompanyMS com;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "cate_id", nullable = false)
//    private CategoryMS cate;
//
//    public CompanyMS getCom() {
//        return com;
//    }
//
//    public void setCom(CompanyMS com) {
//        this.com = com;
//    }
//
//    public CategoryMS getCate() {
//        return cate;
//    }
//
//    public void setCate(CategoryMS cate) {
//        this.cate = cate;
//    }
//
//}