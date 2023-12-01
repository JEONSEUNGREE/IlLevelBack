package com.trip.penguin.follow.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFollowListDTO {

    private List<UserFollowDTO> userFollowList;

    private Integer followCount;

    private Integer totalPage;


}
