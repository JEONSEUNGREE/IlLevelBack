package com.trip.penguin.domain.room;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "ROOM_PIC_MS", schema = "tp-back-app")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomPicMS {

    @Id
    @ManyToOne
    @JoinColumn(name = "roomId", nullable = false)
    private RoomMS roomMs;

    @Column(name = "pic_location", nullable = false)
    private String picLocation;

    @Column(name = "pic_seq", nullable = false)
    private Integer picSeq;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "modified_date", nullable = false)
    private LocalDate modifiedDate;

}