package com.trip.penguin.room;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import com.trip.penguin.company.domain.CompanyMS;
import com.trip.penguin.company.service.CompanyService;
import com.trip.penguin.constant.CommonConstant;
import com.trip.penguin.room.domain.RoomMS;
import com.trip.penguin.room.domain.RoomPicMS;
import com.trip.penguin.room.service.RoomPicService;
import com.trip.penguin.room.service.RoomService;

import jakarta.persistence.EntityManager;

@ActiveProfiles("local")
@DataJpaTest(properties = "classpath:application.yaml")
@ComponentScan(value = "com.trip.penguin")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoomImageCRUDTest {

	private final RoomService roomService;

	private final RoomPicService roomPicService;

	private final CompanyService companyService;

	private final EntityManager em;

	private RoomMS beforeCommitRoomMS;

	private CompanyMS beforeCommitCompany;

	@Autowired
	public RoomImageCRUDTest(RoomService roomService, CompanyService companyService, RoomPicService roomPicService,
		EntityManager em) {
		this.roomService = roomService;
		this.roomPicService = roomPicService;
		this.companyService = companyService;
		this.em = em;
	}

	@BeforeEach
	public void beforeTest() {
		/* 회사 가입 */
		beforeCommitCompany = CompanyMS.builder()
			.com_nm("testNm")
			.comEmail("test@test.com")
			.comPwd("testPwd")
			.comImg("defaultImg")
			.comAddress("location")
			.comApproval(CommonConstant.N.name())
			.userRole("ROLE_COM")
			.build();

		/* 객실 등록 */
		beforeCommitRoomMS = RoomMS.builder()
			.roomNm("testNm")
			.checkIn(LocalDateTime.now())
			.checkOut(LocalDateTime.now())
			.couponYn(CommonConstant.Y.name())
			.sellPrc(120000)
			.maxCount(5)
			.soldOutYn(CommonConstant.N.name())
			.roomDesc("Desc")
			.build();
	}

	@Test
	@DisplayName("객실 이미지 추가, 수정, 삭제, 테스트")
	void createRoomImageTest() {

		// given
		/* 회사 가입 */
		companyService.createCompany(beforeCommitCompany);

		/* 객실 등록 */
		RoomMS afterCommitRoom = roomService.createRoom(beforeCommitRoomMS, beforeCommitCompany, new ArrayList<>());

		em.flush();
		em.clear();

		// when
		/* 객실 조회 */
		RoomMS findRoom = roomService.getRoomById(afterCommitRoom).orElseThrow(IllegalArgumentException::new);

		/* 객실 사진 3개 추가 */
		List<RoomPicMS> roomPicList = new ArrayList<>();

		for (int i = 0; i < 3; i++) {
			roomPicList.add(
				RoomPicMS.builder()
					.roomMs(findRoom)
					.picLocation("change" + i)
					.picSeq(i)
					.build()
			);
		}

		roomPicService.updateRoomPics(findRoom, roomPicList);

		em.flush();
		em.clear();

		// then
		/* 객실 사진 목록 조회 */
		RoomMS findRoom2 = roomService.getRoomById(afterCommitRoom).orElseThrow(IllegalArgumentException::new);

		/* 기존 객실 0번 사진 삭제 확인 */
		assertEquals(findRoom2.getRoomPicList().size(), 3);

		/* 객실 사진 0,1번 2개 확인 */
		assertEquals(findRoom2.getRoomPicList().get(0).getPicLocation(), "change0");
		assertEquals(findRoom2.getRoomPicList().get(1).getPicLocation(), "change1");
		assertEquals(findRoom2.getRoomPicList().get(2).getPicLocation(), "change2");
	}

}
