package com.trip.penguin.room.dto;

import com.trip.penguin.room.domain.RoomPicMS;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppRoomPicDTO {

    private Long id;

    private String picLocation;

    private Integer picSeq;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

}
