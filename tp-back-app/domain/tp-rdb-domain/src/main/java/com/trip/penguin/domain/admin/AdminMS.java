//package com.trip.penguin.domain.admin;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//
//@Entity
//@Table(name = "ADMIN_MS", schema = "tp-back-app")
//public class AdminMS {
//    @Id
//    @Column(name = "admin_id", nullable = false)
//    private String adminId;
//
//    @Column(name = "admin_email", nullable = false)
//    private String adminEmail;
//
//    @Column(name = "admin_pwd", nullable = false)
//    private String adminPwd;
//
//    @Column(name = "admin_nm", nullable = false)
//    private String adminNm;
//
//    public String getAdminId() {
//        return adminId;
//    }
//
//    public void setAdminId(String adminId) {
//        this.adminId = adminId;
//    }
//
//    public String getAdminEmail() {
//        return adminEmail;
//    }
//
//    public void setAdminEmail(String adminEmail) {
//        this.adminEmail = adminEmail;
//    }
//
//    public String getAdminPwd() {
//        return adminPwd;
//    }
//
//    public void setAdminPwd(String adminPwd) {
//        this.adminPwd = adminPwd;
//    }
//
//    public String getAdminNm() {
//        return adminNm;
//    }
//
//    public void setAdminNm(String adminNm) {
//        this.adminNm = adminNm;
//    }
//
//}