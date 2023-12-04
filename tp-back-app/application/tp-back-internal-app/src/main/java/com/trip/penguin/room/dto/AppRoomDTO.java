package com.trip.penguin.room.dto;


import com.trip.penguin.room.domain.RoomPicMS;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppRoomDTO {

    private Long id;

    private String comName;

    private Long comId;

    private String thumbNail;

    private Integer sellPrc;

    private String soldOutYn;

    private String roomDesc;

    private String couponYn;

    private Integer maxCount;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private List<AppRoomPicDTO> roomPicDTOList = new ArrayList<>();

    public void addRoomPicList(List<RoomPicMS> roomPicList) {
        roomPicList.forEach(roomPic ->
                roomPicDTOList.add(
                        AppRoomPicDTO.builder()
                                .id(roomPic.getId())
                                .picSeq(roomPic.getPicSeq())
                                .picLocation(roomPic.getPicLocation())
                                .createdDate(roomPic.getCreatedDate())
                                .modifiedDate(roomPic.getModifiedDate())
                                .build()));
    }
}
