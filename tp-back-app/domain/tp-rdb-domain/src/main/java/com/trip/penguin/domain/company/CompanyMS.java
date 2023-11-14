//package com.trip.penguin.domain.company;
//
//import com.trip.penguin.domain.category.CategoryMS;
//import com.trip.penguin.domain.room.RoomMS;
//import com.trip.penguin.domain.user.UserMS;
//import jakarta.persistence.*;
//
//import java.time.LocalDate;
//import java.util.LinkedHashSet;
//import java.util.Set;
//
//@Entity
//@Table(name = "COMPANY_MS", schema = "tp-back-app")
//public class CompanyMS {
//    @Id
//    @Column(name = "com_id", nullable = false)
//    private Long id;
//
//    @Column(name = "com_nm", nullable = false)
//    private String comNm;
//
//    @Column(name = "com_email", nullable = false)
//    private String comEmail;
//
//    @Column(name = "com_pwd", nullable = false)
//    private String comPwd;
//
//    @Column(name = "accom_nm", nullable = false)
//    private String accomNm;
//
//    @Column(name = "com_img", nullable = false)
//    private String comImg;
//
//    @Column(name = "com_address", nullable = false)
//    private String comAddress;
//
//    @Column(name = "com_approval", nullable = false)
//    private Integer comApproval;
//
//    @Column(name = "user_role", nullable = false)
//    private String userRole;
//
//    @Column(name = "created_date", nullable = false)
//    private LocalDate createdDate;
//
//    @Column(name = "modified_date", nullable = false)
//    private LocalDate modifiedDate;
//
//    @ManyToMany(mappedBy = "com")
//    private Set<CategoryMS> categoryMs = new LinkedHashSet<>();
//
//    @OneToMany(mappedBy = "com")
//    private Set<RoomMS> roomMs = new LinkedHashSet<>();
//
//    @ManyToMany(mappedBy = "com")
//    private Set<UserMS> userMs = new LinkedHashSet<>();
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getComNm() {
//        return comNm;
//    }
//
//    public void setComNm(String comNm) {
//        this.comNm = comNm;
//    }
//
//    public String getComEmail() {
//        return comEmail;
//    }
//
//    public void setComEmail(String comEmail) {
//        this.comEmail = comEmail;
//    }
//
//    public String getComPwd() {
//        return comPwd;
//    }
//
//    public void setComPwd(String comPwd) {
//        this.comPwd = comPwd;
//    }
//
//    public String getAccomNm() {
//        return accomNm;
//    }
//
//    public void setAccomNm(String accomNm) {
//        this.accomNm = accomNm;
//    }
//
//    public String getComImg() {
//        return comImg;
//    }
//
//    public void setComImg(String comImg) {
//        this.comImg = comImg;
//    }
//
//    public String getComAddress() {
//        return comAddress;
//    }
//
//    public void setComAddress(String comAddress) {
//        this.comAddress = comAddress;
//    }
//
//    public Integer getComApproval() {
//        return comApproval;
//    }
//
//    public void setComApproval(Integer comApproval) {
//        this.comApproval = comApproval;
//    }
//
//    public String getUserRole() {
//        return userRole;
//    }
//
//    public void setUserRole(String userRole) {
//        this.userRole = userRole;
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
//    public Set<CategoryMS> getCategoryMs() {
//        return categoryMs;
//    }
//
//    public void setCategoryMs(Set<CategoryMS> categoryMs) {
//        this.categoryMs = categoryMs;
//    }
//
//    public Set<RoomMS> getRoomMs() {
//        return roomMs;
//    }
//
//    public void setRoomMs(Set<RoomMS> roomMs) {
//        this.roomMs = roomMs;
//    }
//
//    public Set<UserMS> getUserMs() {
//        return userMs;
//    }
//
//    public void setUserMs(Set<UserMS> userMs) {
//        this.userMs = userMs;
//    }
//
//}