package com.trip.penguin.room;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

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
import com.trip.penguin.room.service.RoomService;

import jakarta.persistence.EntityManager;

@ActiveProfiles("local")
@DataJpaTest(properties = "classpath:application.yaml")
@ComponentScan(value = "com.trip.penguin")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoomCRUDTest {

	private final RoomService roomService;

	private final CompanyService companyService;

	private final EntityManager em;

	private RoomMS beforeCommitRoomMS;

	private CompanyMS beforeCommitCompany;

	@Autowired
	public RoomCRUDTest(RoomService roomService, CompanyService companyService, EntityManager em) {
		this.roomService = roomService;
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
			.thubNail("default")
			.sellPrc(120000)
			.maxCount(5)
			.soldOutYn(CommonConstant.N.name())
			.roomDesc("Desc")
			.build();
	}

	@Test
	@DisplayName("객실 생성 테스트")
	void createRoomTest() {

		// given
		/* 회사 가입 */
		companyService.createCompany(beforeCommitCompany);

		/* 객실 등록, 객실 기본 이미지 생성 */
		RoomMS afterCommitRoom = roomService.createRoom(beforeCommitRoomMS, beforeCommitCompany, new ArrayList<>());

		em.flush();
		em.clear();

		// when
		/* 객실 조회 */
		RoomMS findRoom = roomService.getRoomById(afterCommitRoom).orElseThrow(IllegalArgumentException::new);

		// then
		assertEquals(afterCommitRoom.getId(), findRoom.getId());
	}

	@Test
	@DisplayName("객실 정보 수정 테스트")
	void updateRoomTest() {

		// given
		/* 회사 가입 */
		companyService.createCompany(beforeCommitCompany);

		/* 객실 등록 */
		RoomMS afterCommitRoom = roomService.createRoom(beforeCommitRoomMS, beforeCommitCompany, new ArrayList<>());

		em.flush();
		em.clear();

		// when
		/* 객실 조회 */
		RoomMS findRoom = roomService.getRoomById(afterCommitRoom).orElseThrow(IllegalAccessError::new);
		findRoom.setRoomNm("changeNm");

		roomService.updateRoom(findRoom);

		em.flush();
		em.clear();

		// then

		/* 객실 조회 */
		RoomMS confirmRoom = roomService.getRoomById(afterCommitRoom).orElseThrow(IllegalAccessError::new);

		assertEquals(afterCommitRoom.getId(), confirmRoom.getId());
		assertNotEquals(afterCommitRoom.getRoomNm(), confirmRoom.getRoomNm());
		assertEquals(confirmRoom.getRoomNm(), "changeNm");
	}

	@Test
	@DisplayName("객실 삭제 테스트")
	void deleteRoomTest() {

		// given
		/* 회사 가입 */
		companyService.createCompany(beforeCommitCompany);

		/* 객실 등록 */
		RoomMS afterCommitRoom = roomService.createRoom(beforeCommitRoomMS, beforeCommitCompany, new ArrayList<>());

		em.flush();
		em.clear();

		// when
		/* 객실 삭제 */
		roomService.deleteRoom(afterCommitRoom);

		em.flush();
		em.clear();

		// then
		/* 객실 삭제 확인 */
		assertFalse(roomService.getRoomById(afterCommitRoom).isPresent());
	}

}
