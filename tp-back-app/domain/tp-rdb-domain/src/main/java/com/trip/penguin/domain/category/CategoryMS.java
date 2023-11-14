//package com.trip.penguin.domain.category;
//
//import com.trip.penguin.domain.company.CompanyMS;
//import jakarta.persistence.*;
//
//import java.time.LocalDate;
//import java.util.LinkedHashSet;
//import java.util.Set;
//
//@Entity
//@Table(name = "CATEGORY_MS", schema = "tp-back-app")
//public class CategoryMS {
//    @Id
//    @Column(name = "cate_id", nullable = false)
//    private Long id;
//
//    @Column(name = "parent_id")
//    private Long parentId;
////
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "child_id")
//    private CategoryMS child;
//
//    @Column(name = "cate_nm", nullable = false)
//    private String cateNm;
//
//    @Column(name = "created_date", nullable = false)
//    private LocalDate createdDate;
//
//    @Column(name = "modified_date", nullable = false)
//    private LocalDate modifiedDate;
//
//    @ManyToMany(mappedBy = "cate")
//    private Set<CompanyMS> companyMs = new LinkedHashSet<>();
//
//    @OneToMany(mappedBy = "child")
//    private Set<CategoryMS> categoryMs = new LinkedHashSet<>();
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getParentId() {
//        return parentId;
//    }
//
//    public void setParentId(Long parentId) {
//        this.parentId = parentId;
//    }
//
//    public CategoryMS getChild() {
//        return child;
//    }
//
//    public void setChild(CategoryMS child) {
//        this.child = child;
//    }
//
//    public String getCateNm() {
//        return cateNm;
//    }
//
//    public void setCateNm(String cateNm) {
//        this.cateNm = cateNm;
//    }
//
//    public LocalDate getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(LocalDate createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    public LocalDate getModifiedDate() {
//        return modifiedDate;
//    }
//
//    public void setModifiedDate(LocalDate modifiedDate) {
//        this.modifiedDate = modifiedDate;
//    }
//
//    public Set<CompanyMS> getCompanyMs() {
//        return companyMs;
//    }
//
//    public void setCompanyMs(Set<CompanyMS> companyMs) {
//        this.companyMs = companyMs;
//    }
//
//    public Set<CategoryMS> getCategoryMs() {
//        return categoryMs;
//    }
//
//    public void setCategoryMs(Set<CategoryMS> categoryMs) {
//        this.categoryMs = categoryMs;
//    }
//
//}