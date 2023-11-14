//package com.trip.penguin.domain.room;
//
//import jakarta.persistence.*;
//
//import java.time.LocalDate;
//
//@Entity
//@Table(name = "ROOM_PIC_MS", schema = "tp-back-app")
//public class RoomPicMS {
//    @Id
//    @Column(name = "roomId", nullable = false)
//    private Long id;
//
//    @MapsId
//    @OneToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "roomId", nullable = false)
//    private RoomMS roomMs;
//
//    @Column(name = "pic_location", nullable = false)
//    private String picLocation;
//
//    @Column(name = "pic_seq", nullable = false)
//    private Integer picSeq;
//
//    @Column(name = "created_date", nullable = false)
//    private LocalDate createdDate;
//
//    @Column(name = "modified_date", nullable = false)
//    private LocalDate modifiedDate;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public RoomMS getRoomMs() {
//        return roomMs;
//    }
//
//    public void setRoomMs(RoomMS roomMs) {
//        this.roomMs = roomMs;
//    }
//
//    public String getPicLocation() {
//        return picLocation;
//    }
//
//    public void setPicLocation(String picLocation) {
//        this.picLocation = picLocation;
//    }
//
//    public Integer getPicSeq() {
//        return picSeq;
//    }
//
//    public void setPicSeq(Integer picSeq) {
//        this.picSeq = picSeq;
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
//}