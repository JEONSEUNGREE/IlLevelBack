package com.trip.penguin.recommand;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import com.trip.penguin.recommand.room.repository.RoomCustomRepository;

@ActiveProfiles("local")
// @DataJpaTest가 빈설정에서 오류 발생
// 해결이 안되서 일단 SpringBootTest로 진행
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RecRoomDataTest {

	private RoomCustomRepository roomCustomRepository;

	@Autowired
	public RecRoomDataTest(RoomCustomRepository roomCustomRepository) {
		this.roomCustomRepository = roomCustomRepository;
	}

	@Test
	@DisplayName("리뷰 생성, 조회 테스트")
	void createRoomTest() {

		Pageable pageable = PageRequest.of(1, 2);
		roomCustomRepository.getMainRecRoom(pageable);

	}

}
