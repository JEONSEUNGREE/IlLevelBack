package com.trip.penguin.room.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ROOM_PIC_MS", schema = "tp-back-app")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomPicMS {

	@Id
	@Column(name = "room_pic_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id", nullable = false)
	private RoomMS roomMs;

	@Column(name = "pic_location", nullable = false)
	private String picLocation;

	@Column(name = "pic_seq", nullable = false)
	private Integer picSeq;

	@Column(name = "created_date", nullable = false)
	private LocalDateTime createdDate;

	@Column(name = "modified_date", nullable = false)
	private LocalDateTime modifiedDate;

	public RoomPicMS createDefaultPic(RoomMS roomMS) {
		return RoomPicMS.builder()
			.picLocation("defaultImg")
			.picSeq(0)
			.roomMs(roomMS)
			.createdDate(LocalDateTime.now())
			.modifiedDate(LocalDateTime.now())
			.build();
	}

}