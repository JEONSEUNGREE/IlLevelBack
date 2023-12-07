package com.trip.penguin.room.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.trip.penguin.booking.domain.BookingMS;
import com.trip.penguin.company.domain.CompanyMS;
import com.trip.penguin.constant.CommonConstant;
import com.trip.penguin.review.domain.ReviewMS;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ROOM_MS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomMS {

	@Id
	@Column(name = "room_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Builder.Default
	@OneToMany(mappedBy = "roomMs", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<RoomPicMS> roomPicList = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "roomMS", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReviewMS> reviewList = new ArrayList<>();

	/* 빌더 패턴의 리스트의 경우 일반 적인 필드 초기화 과정을 거치지 않아 원시형이 아닌경우 null 발생*/
	@Builder.Default
	@OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<BookingMS> bookingList = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "com_id", nullable = false)
	private CompanyMS com;

	@Column(name = "thumbnail", nullable = false)
	private String thumbNail;

	@Column(name = "room_nm", nullable = false)
	private String roomNm;

	@Column(name = "com_name", nullable = false)
	private String comName;

	@Column(name = "sell_prc", nullable = false)
	private Integer sellPrc;

	@Column(name = "sold_out", nullable = false)
	private String soldOutYn;

	@Column(name = "check_in", nullable = false)
	private LocalDateTime checkIn;

	@Column(name = "check_out", nullable = false)
	private LocalDateTime checkOut;

	@Column(name = "room_desc")
	private String roomDesc;

	@Column(name = "coupon_yn", nullable = false, length = 2)
	private String couponYn;

	@Column(name = "max_count", nullable = false)
	private Integer maxCount;

	@Column(name = "created_date", nullable = false)
	private LocalDateTime createdDate;

	@Column(name = "modified_date", nullable = false)
	private LocalDateTime modifiedDate;

	public void createRoomMs() {
		this.soldOutYn = CommonConstant.N.name();
		this.setCreatedDate(LocalDateTime.now());
		this.setModifiedDate(LocalDateTime.now());
	}

	public void setCompanyInfo(CompanyMS companyInfo) {
		this.com = companyInfo;
		this.comName = companyInfo.getCom_nm();
	}

	public void setThumbNailOrDefaultImg(String thumbNailImg) {
		if (!"".equals(thumbNailImg)) {
			this.setThumbNail(thumbNailImg);
		} else {
			this.setThumbNail("defaultImg");
		}
	}

	/**
	 * 기본 이미지 생성
	 */
	public void addDefaultRoomPic() {
		RoomPicMS roomPicMS = new RoomPicMS().createDefaultPic(this);
		this.roomPicList.add(roomPicMS);
	}

	/**
	 * 이미지 추가 - 이미지 목록
	 * @param roomPicNameList - 이미지 목록
	 */
	public List<RoomPicMS> addRoomPics(List<String> roomPicNameList) {

		if (!roomPicNameList.isEmpty()) {
			List<RoomPicMS> tmpPicList = new ArrayList<>();
			AtomicInteger sequence = new AtomicInteger();
			roomPicNameList.forEach(fileName -> {
				tmpPicList.add(RoomPicMS.builder()
						.roomMs(this)
						.picLocation(fileName)
						.picSeq(sequence.getAndIncrement())
						.createdDate(LocalDateTime.now())
						.modifiedDate(LocalDateTime.now())
						.build());
			});
			this.roomPicList = tmpPicList;
		} else {
			addDefaultRoomPic();
		}
		return this.roomPicList;
	}

	/**
	 * 이미지 수정
	 * @param modifyPicList - 수정, 추가 이미지 목록
	 * @return List<RoomPicMS> - 삭제 이미지 목록
	 */
	public List<RoomPicMS> addModifyRoomPics(List<RoomPicMS> modifyPicList) {

		List<RoomPicMS> tmpModifedList = new ArrayList<>();
		Map<Long, RoomPicMS> stdList = new HashMap<>();

		/* 기존 사진 Key, Map 설정 */
		this.roomPicList.forEach(item -> stdList.put(item.getId(), item));

		/*
		루프
		1. 기존 사진의 경우 업데이트
		2. 없는 경우 생성
		3. 없어진 경우 삭제
		*/
		modifyPicList.forEach(item -> {

			if (item.getId() != null && stdList.get(item.getId()) != null) {
				RoomPicMS existRoom = stdList.get(item.getId());
				existRoom.setRoomMs(this);
				existRoom.setPicSeq(item.getPicSeq());
				existRoom.setModifiedDate(item.getModifiedDate());

				stdList.remove(item.getId());
				tmpModifedList.add(existRoom);
			} else {
				item.setRoomMs(this);
				item.setPicSeq(item.getPicSeq());
				item.setModifiedDate(LocalDateTime.now());
				item.setCreatedDate(LocalDateTime.now());
				tmpModifedList.add(item);
			}
		});

		this.roomPicList = tmpModifedList;
		return stdList.values().stream().toList();
	}

	public void checkStock(int buyStock) {

		if (this.maxCount < buyStock) {
			throw new RuntimeException("잔여 객실이 없습니다.");
		}
	}

	public void decreaseStock(int count) {

		this.maxCount = this.maxCount - count;
	}
}