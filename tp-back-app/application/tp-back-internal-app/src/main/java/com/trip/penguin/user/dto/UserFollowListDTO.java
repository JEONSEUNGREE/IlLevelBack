package com.trip.penguin.user.dto;


import lombok.*;

import java.util.ArrayList;
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
